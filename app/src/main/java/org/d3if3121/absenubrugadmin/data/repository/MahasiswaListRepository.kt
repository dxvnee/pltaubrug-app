package org.d3if3121.absenubrugadmin.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Jam
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.data.model.Response
import org.d3if3121.absenubrugadmin.data.repository.interfaces.GetJamResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.MahasiswaListInterface
import java.io.IOException


class MahasiswaListRepository (
    private val mahasiswaRef: CollectionReference,
    private val absenRef: CollectionReference,
    private val dataRef: CollectionReference,
): MahasiswaListInterface {
    override fun getAbsenList(user: Mahasiswa) = callbackFlow {
        val absenRef = absenRef.document(user.nip).collection("tanggal")

        val listener = absenRef
            .addSnapshotListener { snapshot, e ->
                if (e != null){
                    Response.Failure(e)
                    return@addSnapshotListener
                }

                absenRef.get(Source.SERVER).addOnSuccessListener { freshSnapshot ->
                    val absenListResponse =
                        if (snapshot != null) {
                            val absenList = snapshot.map {
                                Log.d("kerer", it.toString())
                                it.toAbsen()
                            }
                            Response.Success(absenList)
                        } else {
                            Response.Failure(e)
                        }
                    trySend(absenListResponse)
                }
            }

        awaitClose {
            listener.remove()
        }
    }



    override fun getMahasiswaList() = callbackFlow {

        val listener = mahasiswaRef
            .addSnapshotListener { snapshot, e ->
                val mahasiswaListResponse =
                    if (snapshot != null) {
                        val mahasiswaList = snapshot.map { it.toMahasiswa() }
                        Response.Success(mahasiswaList)
                    } else {
                        Response.Failure(e)
                    }
                trySend(mahasiswaListResponse)
            }

        awaitClose {
            listener.remove()
        }
    }

    override fun addUser(mahasiswa: Mahasiswa) = callbackFlow {
        val listener = mahasiswaRef.whereEqualTo("nim", mahasiswa.nip)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val updatedMahasiswa = snapshot.documents.first().toMahasiswa()
                    trySend(Response.Success(updatedMahasiswa))
                } else if (e != null) {
                    trySend(Response.Failure(e))
                } else {
                    trySend(Response.Failure(Exception("User not found!")))
                }
            }

        awaitClose {
            listener.remove()
        }
    }


    override suspend fun addMahasiswa(mahasiswa: Mahasiswa) = try {
        val mahasiswaSama = mahasiswaRef.whereEqualTo("nim", mahasiswa.nip).get(Source.SERVER).await()


        if (mahasiswaSama.isEmpty){
            val id = mahasiswaRef.add(mahasiswa).await().id
            Response.Success(id)
        } else {
            Response.Failure(Exception("NIM already registered."))
        }
    } catch (e: Exception){
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
    override suspend fun fetchImageFromFirebase(filePath: String) = try {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("images/$filePath") // Perbaiki path ke file spesifik
        val imageUrl = storageRef.downloadUrl.await() // Dapatkan URL file
        Response.Success(imageUrl)
    } catch (e: Exception) {
        Response.Failure(e)
    }



    override suspend fun addFotoProfil(nip: String, uri: Uri) = try {
        val query = mahasiswaRef.whereEqualTo("nip", nip).get().await()

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

    override suspend fun addAbsen(absen: Absen) = try {
        val idRef = mahasiswaRef.document(absen.nip)
        val pegawaiRef = idRef.collection("tanggal").document(absen.tanggal)

        if (absen.foto != null) {
            val imageUrl = uploadImagetoFirebase(absen.foto.uri, "${absen.tanggal}_${absen.nip}_Masuk")
            pegawaiRef.update("image", imageUrl).await()
        }

        Response.Success(absen.nip)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addAbsenPulang(absen: Absen) = try {
        val idRef = absenRef.document(absen.nip)
        Log.d("hehe", absen.tanggal)

        val pegawaiRef = idRef.collection("tanggal").document(absen.tanggal)


        pegawaiRef.update("keterangan2", absen.keterangan2).await()
        pegawaiRef.update("absen2", absen.absen2).await()
        pegawaiRef.update("foto2", absen.foto2).await()
        pegawaiRef.update("deskripsi2", absen.deskripsi2).await()
        pegawaiRef.update("lokasi2", absen.lokasi2).await()
        pegawaiRef.update("jam2", absen.jam2).await()
        pegawaiRef.update("telat2", absen.telat2).await()
        pegawaiRef.update("jamtelat2", absen.jamtelat2).await()

        if (absen.foto2 != null) {
            val imageUrl = uploadImagetoFirebase(absen.foto2.uri, "${absen.tanggal}_${absen.nip}_Pulang")
            pegawaiRef.update("image", imageUrl).await()
        }

        Response.Success(absen.nip)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun editAbsen(absen: Absen, isPulang: Boolean) = try {
        val idRef = absenRef.document(absen.nip)
        val pegawaiRef = idRef.collection("tanggal").document(absen.tanggal)

        if (!isPulang){
            pegawaiRef.update("keterangan", absen.keterangan).await()
            pegawaiRef.update("deskripsi", absen.deskripsi).await()
            pegawaiRef.update("jam", absen.jam).await()
            pegawaiRef.update("telat", absen.telat).await()
            pegawaiRef.update("jamtelat", absen.jamtelat).await()
        } else {
            pegawaiRef.update("keterangan2", absen.keterangan).await()
            pegawaiRef.update("deskripsi2", absen.deskripsi).await()
            pegawaiRef.update("jam2", absen.jam).await()
            pegawaiRef.update("telat2", absen.telat).await()
            pegawaiRef.update("jamtelat2", absen.jamtelat).await()
        }

        Response.Success(absen.nip)
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override suspend fun editRole(pegawai: Mahasiswa) = try {
        val query = mahasiswaRef.whereEqualTo("nip", pegawai.nip).get().await()

        if(query.isEmpty){
            Response.Failure(Exception("Tidak ditemukan data"))
        } else {
            query.documents.first().reference.update("role", pegawai.role)
            Response.Success(pegawai.nip)
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override suspend fun deleteAbsen(absen: Absen) = try {
        val idRef = absenRef.document(absen.nip)
        val pegawaiRef = idRef.collection("tanggal").document(absen.tanggal)

        pegawaiRef.delete().await()
        Response.Success(absen.nip)
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override fun getMahasiswa(nip: String) = callbackFlow {
        val listener = mahasiswaRef.document(nip)
            .addSnapshotListener { snapshot, e ->
                val mahasiswaResponse = if(snapshot != null){
                    val data = snapshot.toMahasiswa()
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

    override suspend fun editMahasiswa(mahasiswabaru: Mahasiswa) = try {
        Log.d("waow2", mahasiswabaru.toString())
        val mahasiswa = mahasiswaRef.document(mahasiswabaru.nip)


        if (mahasiswa.get().await().exists()){
            mahasiswa.update(
                mapOf(
                    "nama" to mahasiswabaru.nama,
                    "posisi" to mahasiswabaru.posisi
                )
            )
            Response.Success("Data berhasil diubah!")
        } else {
            Response.Failure(Exception("nip already registered."))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun editJam(jam: Jam) = try {
        val mahasiswa = dataRef.document("jam")

        mahasiswa.update(
            mapOf(
                "masuk" to jam.masuk,
                "keluar" to jam.keluar
            )
        )
        Response.Success("Jam berhasil diubah!")
    } catch (e: Exception){
        Response.Failure(e)
    }

    override fun getJam() = callbackFlow {
        val listener = dataRef.document("jam")
            .addSnapshotListener { snapshot, e ->
                val mahasiswaResponse = if(snapshot != null){
                    val data = snapshot.toJam()
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


    override suspend fun loginMahasiswa(nim: String, password: String) = try {
        val docmahasiswa = mahasiswaRef.whereEqualTo("nip", nim).get(Source.SERVER).await()
        if (!docmahasiswa.isEmpty){
            val mahasiswa = docmahasiswa.first().toMahasiswa()

            if (mahasiswa.password == password){
                if("ADMIN" in mahasiswa.role){
                    Response.Success(mahasiswa)
                } else {
                    Response.Failure(Exception("Anda tidak memiliki izin admin!"))
                }
            } else {
                Response.Failure(Exception("Incorrect Password."))
            }
        } else {
            Response.Failure(Exception("NIM doesn't exist."))
        }
    } catch (e: Exception){
        Log.e("Firestore", e.toString())
        Response.Failure(Exception("Error"))
    }

    suspend fun <T> retryWithDelay(
        maxRetryTime: Long = 30_000L,
        retryDelay: Long = 5_000,
        action: suspend () -> Response<T>
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
        return Response.Failure(Exception("Gagal mengambil data!"))
    }


}

fun DocumentSnapshot.toMahasiswa() = Mahasiswa(
    nama = getString(Mahasiswa.NAMA) ?: "Default",
    password = getString(Mahasiswa.PASSWORD) ?: "Default",
    nip = getString(Mahasiswa.NIP)?: "DefaultName",
    foto = getString(Mahasiswa.FOTO)?: "DefaultName",
    role = get(Mahasiswa.ROLE) as? List<String> ?: emptyList(),
    posisi = getString(Mahasiswa.POSISI)?: "-",


)

fun DocumentSnapshot.toJam() = Jam(
    masuk = getString(Jam.MASUK) ?: "07:00",
    keluar = getString(Jam.KELUAR) ?: "16:00"
)

fun DocumentSnapshot.toAbsen(): Absen = Absen(
    nama = getString("nama") ?: "",
    nip = getString("nip") ?: "",
    keterangan = getString("keterangan") ?: "Belum Absen",
    deskripsi = getString("deskripsi") ?: "",
    tanggal = getString("tanggal") ?: "",
    jam = getString("jam") ?: "-",
    telat = getString("telat") ?: "",
    jamtelat = getString("jamtelat") ?: "",
    jamtarget = getString("jamtarget") ?: "07:00",
    absen = getString("absen") ?: "",

    lokasi = getString("lokasi") ?: "",
    image =  getString("image") ?: "",
    image2 =  getString("image2") ?: "",
    keterangan2 = getString("keterangan2") ?: "Belum Absen",
    deskripsi2 = getString("deskripsi2") ?: "",
    jam2 = getString("jam2") ?: "-",
    lokasi2 = getString("lokasi2") ?: "",
    telat2 = getString("telat2") ?: "",
    jamtelat2 = getString("jamtelat2") ?: "",
    absen2 = getString("absen2") ?: ""
)
