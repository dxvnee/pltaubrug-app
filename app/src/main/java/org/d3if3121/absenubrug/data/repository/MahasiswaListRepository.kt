package org.d3if3121.absenubrug.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.ImageUpload
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.MahasiswaEdit
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.data.repository.interfaces.MahasiswaListInterface

class MahasiswaListRepository (
    private val mahasiswaRef: CollectionReference,
    private val absenRef: CollectionReference
): MahasiswaListInterface {
    override fun getAbsenList(user: Mahasiswa) = callbackFlow {
        val absenRef = absenRef.document(user.nip).collection("tanggal")

        val listener = absenRef
            .addSnapshotListener { snapshot, e ->
                val absenListResponse =
                    if (snapshot != null) {
                        val absenList = snapshot.map { it.toAbsen() }
                        Response.Success(absenList)
                    } else {
                        Response.Failure(e)
                    }
                trySend(absenListResponse)
            }

        awaitClose {
            listener.remove()
        }
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



    override suspend fun addMahasiswa(mahasiswa: Mahasiswa) = try {
        val mahasiswaSama = mahasiswaRef.whereEqualTo("nip", mahasiswa.nip).get(Source.SERVER).await()

        if (mahasiswaSama.isEmpty){
            val id = mahasiswaRef.add(mahasiswa).await().id
            Response.Success(id)
        } else {
            Response.Failure(Exception("nip already registered."))
        }
    } catch (e: Exception){
        Response.Failure(e)
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

    override suspend fun addAbsen(absen: Absen) = try {
        val idRef = absenRef.document(absen.nip)
        val pegawaiRef = idRef.collection("tanggal").document(absen.tanggal)

        pegawaiRef.set(absen).await()

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
            pegawaiRef.update("image2", imageUrl).await()
        }

        Response.Success(absen.nip)
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



    override suspend fun loginMahasiswa(nip: String, password: String) = try {
        val docmahasiswa = mahasiswaRef.whereEqualTo("nip", nip).get(Source.SERVER).await()
        if (!docmahasiswa.isEmpty){
            val mahasiswa = docmahasiswa.first().toMahasiswa()

            if (mahasiswa.password == password){
                if(("PEGAWAI" in mahasiswa.role) || ("SHEET" in mahasiswa.role )){
                    Response.Success(mahasiswa)
                } else {
                    Response.Failure(Exception("Anda belum menerima perizinan."))
                }
            } else {
                Response.Failure(Exception("Password Salah."))
            }
        } else {
            Response.Failure(Exception("NIP tidak terdaftar."))
        }
    } catch (e: Exception){
        Response.Failure(Exception("Error"))
    }

}

fun DocumentSnapshot.toMahasiswa() = Mahasiswa(
    nama = getString(Mahasiswa.NAMA) ?: "null",
    password = getString(Mahasiswa.PASSWORD) ?: "null",
    nip = getString(Mahasiswa.NIP)?: "null",
    role = get(Mahasiswa.ROLE) as? List<String> ?: emptyList(),
    foto = getString(Mahasiswa.FOTO)?: "null",
    posisi = getString(Mahasiswa.POSISI)?: "-",
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
