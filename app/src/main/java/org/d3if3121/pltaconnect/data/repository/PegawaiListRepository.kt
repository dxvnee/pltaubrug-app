package org.d3if3121.pltaconnect.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.PegawaiEdit
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.data.model.Sheet
import org.d3if3121.pltaconnect.data.model.SuccessResponse
import org.d3if3121.pltaconnect.data.model.data.Debit
import org.d3if3121.pltaconnect.data.model.data.Masuk
import org.d3if3121.pltaconnect.data.model.data.MasukRequest
import org.d3if3121.pltaconnect.data.model.data.Pemakaian
import org.d3if3121.pltaconnect.data.model.data.PemakaianRequest
import org.d3if3121.pltaconnect.data.model.data.ProduksiRequest
import org.d3if3121.pltaconnect.data.model.data.Produksi
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListInterface
import java.io.IOException

class PegawaiListRepository (
    private val pegawaiRef: CollectionReference,
    private val debitRef: CollectionReference,
    private val masukRef: CollectionReference,
    private val pemakaianRef: CollectionReference,
    private val produksiRef: CollectionReference,
    private val sheetRef: CollectionReference,
): PegawaiListInterface {
    private val sheetid = "1"

    override fun getPegawaiList() = callbackFlow {
        val listener = pegawaiRef
            .orderBy("nama")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Response.Failure(e))
                    return@addSnapshotListener
                }

                pegawaiRef.orderBy("nama")
                    .get(Source.SERVER)
                    .addOnSuccessListener { freshSnapshot ->
                        val pegawaiList = freshSnapshot.map { it.toPegawai() }
                        trySend(Response.Success(pegawaiList))
                    }
                    .addOnFailureListener { serverError ->
                        trySend(Response.Failure(serverError))
                    }
            }

        awaitClose {
            listener.remove()
        }
    }




    override suspend fun addPegawai(pegawai: Pegawai) = try {
        val pegawaiSama = pegawaiRef.whereEqualTo("nip", pegawai.nip).get().await()

        if (pegawaiSama.isEmpty) {
            val id = pegawaiRef.add(pegawai).await().id
            Response.Success(id)
        } else {
            Response.Failure(Exception("NIM already registered."))
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override suspend fun updatePegawai(pegawai: PegawaiEdit) = try {
        val querySnapshot = pegawaiRef.whereEqualTo("nip", pegawai.nip).get().await()

        if (!querySnapshot.isEmpty) {
            val pegawaiDocument = querySnapshot.documents.first()
            pegawaiDocument.reference.update(
                mapOf(
                    Pegawai.NAMA to pegawai.nama,
                    Pegawai.ROLE to pegawai.major,
                )
            ).await()
            Response.Success("Edit Success.")
        } else {
            Response.Failure(Exception("Edit Failed."))
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun getMahasiswa(nip: String) = callbackFlow {
        val listener = pegawaiRef.document(nip)
            .addSnapshotListener { snapshot, e ->
                val mahasiswaResponse = if(snapshot != null){
                    val data = snapshot.toPegawai()
                    Response.Success(data)
                } else {
                    Response.Failure(e)
                }
                trySend(mahasiswaResponse)
            }

        awaitClose{
            listener.remove()
        }
    }

    override suspend fun loginPegawai(nip: String, password: String) = try {
        val docpegawai = pegawaiRef.whereEqualTo("nip", nip).get().await()

        if (!docpegawai.isEmpty){
            val pegawai = docpegawai.first().toPegawai()
            if (pegawai.password == password){
                if("SHEET" in pegawai.role){
                    Response.Success(pegawai)
                } else{
                    Response.Failure(Exception("Anda tidak memiliki akses."))
                }
            } else {
                Response.Failure(Exception("Incorrect Password."))
            }

        } else {
            Response.Failure(Exception("NIM doesn't exist." + nip + "?"))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }

    suspend fun <T : Any> addData(ref: CollectionReference, data: T, id: String): Response<String> {
        return try {
            val docRef = ref.document(id)
            val idSheetResponse = getSheet(sheetid)

            if (idSheetResponse is Response.Success) {
                docRef.set(data).await()
                Response.Success("Berhasil input!", idSheetResponse.data!!.link)
            } else {
                Response.Failure(Exception("Gagal mendapatkan sheet"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addDebit(debit: Debit) = addData(debitRef, debit, debit.id)
    override suspend fun addProduksi(produksi: ProduksiRequest) = addData(produksiRef, produksi, produksi.id)
    override suspend fun addPemakaian(pemakaian: PemakaianRequest) = addData(pemakaianRef, pemakaian, pemakaian.id)
    override suspend fun addMasuk(masuk: MasukRequest) = addData(masukRef, masuk, masuk.id)



    // Fungsi retry yang bisa digunakan ulang
    suspend fun <T> retryWithDelay(
        maxRetryTime: Long = 30_000L, // Maksimal waktu retry 30 detik
        retryDelay: Long = 5_000L, // Delay antar percobaan
        action: suspend () -> Response<T> // Aksi yang akan dicoba ulang
    ): Response<T> {
        val startTime = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTime < maxRetryTime) {
            try {
                return action()
            } catch (e: Exception) {
                when (e) {
                    is FirebaseFirestoreException -> {
                        if (e.code == FirebaseFirestoreException.Code.UNAVAILABLE) {
                            delay(retryDelay)
                        } else {
                            return Response.Failure(e)
                        }
                    }
                    is IOException -> delay(retryDelay)
                    else -> return Response.Failure(e)
                }
            }
        }

        return Response.Failure(Exception("Gagal mengambil data setelah 30 detik!"))
    }

    suspend fun <T : Any> getData(
        ref: CollectionReference,
        id: String,
        dataclass: Class<T>
    ): Response<T> {
        return retryWithDelay {
            val snapshot = ref.document(id).get().await()
            if (snapshot.exists()) {
                val data = snapshot.toObject(dataclass)
                Response.Success(data)
            } else {
                Response.Failure(Exception("Data tidak ada!"))
            }
        }
    }




    override suspend fun getDebit(id: String) = getData(debitRef, id, Debit::class.java)
    override suspend fun getMasuk(id: String) = getData(masukRef, id, MasukRequest::class.java)
    override suspend fun getPemakaian(id: String) = getData(pemakaianRef, id, PemakaianRequest::class.java)
    override suspend fun getProduksi(id: String) = getData(produksiRef, id, ProduksiRequest::class.java)


    override suspend fun getSheet(id: String): Response<Sheet> = try {
        val snapshot = sheetRef.whereEqualTo("id", id).get().await()

        if (!snapshot.isEmpty) {
            val sheet = snapshot.documents.first().toObject(Sheet::class.java)
            if (sheet != null) {
                Response.Success(sheet)
            } else {
                Response.Failure(Exception("Data tidak ditemukan."))
            }
        } else {
            Response.Failure(Exception("Data tidak ditemukan."))
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }




    override suspend fun addFotoProfil(nip: String, uri: Uri) = try {
        val query = pegawaiRef.whereEqualTo("nip", nip).get().await()

        if (uri != null) {
            val imageUrl = uploadImagetoFirebase(uri, "${nip}_Foto", "fotoprofil/")
            query.documents.first().reference.update("foto", imageUrl)
            Response.Success(imageUrl)
        } else {
            Response.Failure(Exception("Tidak dapat mengupload foto."))
        }

    } catch (e: Exception) {
        Response.Failure(e)
    }

    suspend fun uploadImagetoFirebase(uri: Uri, id: String, path: String = "images/"): String {

        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child(path + id)
        val uploadTask = storageReference.putFile(uri)

        Log.d("STORAGE", storage.toString())
        return try {
            uploadTask.await()
            storageReference.downloadUrl.await().toString()
        } catch (e: Exception) {
            ""
        }
    }



}

fun DocumentSnapshot.toPegawai() = Pegawai(
    nama = getString(Pegawai.NAMA) ?: "null",
    password = getString(Pegawai.PASSWORD) ?: "null",
    nip = getString(Pegawai.NIP)?: "null",
    role = get(Pegawai.ROLE) as? List<String> ?: emptyList(),
    foto = getString(Pegawai.FOTO)?: "null",
)