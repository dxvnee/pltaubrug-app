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
        Log.d("user", user.nim)
        val absenRef = absenRef.document(user.nim).collection("tanggal")

        val listener = absenRef
            .addSnapshotListener { snapshot, e ->
                val absenListResponse =
                    if (snapshot != null) {
                        val absenList = snapshot.map { it.toAbsen() }
                        Log.d("leole", absenList.toString())

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



    override suspend fun getMahasiswaList() = try {
        val mahasiswaSama = mahasiswaRef.get(Source.SERVER).await()

        if (!mahasiswaSama.isEmpty){
            val mahasiswaList = mahasiswaSama.documents.map{
                it.toMahasiswa()
            }
            Response.Success(mahasiswaList)
        } else {
            Response.Failure(Exception("Tidak ada data!"))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }



    override fun addUser(mahasiswa: Mahasiswa) = callbackFlow {
        val listener = mahasiswaRef.whereEqualTo("nim", mahasiswa.nim)
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
        val mahasiswaSama = mahasiswaRef.whereEqualTo("nim", mahasiswa.nim).get(Source.SERVER).await()

        if (mahasiswaSama.isEmpty){
            val id = mahasiswaRef.add(mahasiswa).await().id
            Response.Success(id)
        } else {
            Response.Failure(Exception("NIM already registered."))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }

    suspend fun uploadImagetoFirebase(uri: Uri, id: String): String {

        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child("images/" + id)
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

    override suspend fun editAbsen(absen: Absen) = try {
        val idRef = absenRef.document(absen.nip)
        val pegawaiRef = idRef.collection("tanggal").document(absen.tanggal)

        pegawaiRef.update("keterangan", absen.keterangan).await()
        pegawaiRef.update("deskripsi", absen.deskripsi).await()
        pegawaiRef.update("jam", absen.jam).await()
        pegawaiRef.update("telat", absen.telat).await()
        pegawaiRef.update("jamtelat", absen.jamtelat).await()

        Response.Success(absen.nip)
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






    override suspend fun loginMahasiswa(nim: String, password: String) = try {
        val docmahasiswa = mahasiswaRef.whereEqualTo("nim", nim).get(Source.SERVER).await()
        if (!docmahasiswa.isEmpty){
            val mahasiswa = docmahasiswa.first().toMahasiswa()

            if (mahasiswa.password == password){
                Log.d("lewat", "keren")
                Response.Success(mahasiswa)

            } else {
                Response.Failure(Exception("Incorrect Password."))
            }
        } else {
            Response.Failure(Exception("NIM doesn't exist."))
        }
    } catch (e: Exception){
        Response.Failure(Exception("Error"))
    }

}

fun DocumentSnapshot.toMahasiswa() = Mahasiswa(
    nama = getString(Mahasiswa.NAMA) ?: "Default",
    password = getString(Mahasiswa.PASSWORD) ?: "Default",
    nim = getString(Mahasiswa.NIM)?: "DefaultName",
    jurusan = getString(Mahasiswa.JURUSAN)?: "DefaultName",
    requests = get(Mahasiswa.REQUESTS) as? List<String> ?: emptyList(),
    accept = get(Mahasiswa.ACCEPT) as? List<String> ?: emptyList(),
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
    keterangan2 = getString("keterangan2") ?: "Belum Absen",
    deskripsi2 = getString("deskripsi2") ?: "",
    jam2 = getString("jam2") ?: "-",
    lokasi2 = getString("lokasi2") ?: "",
    telat2 = getString("telat2") ?: "",
    jamtelat2 = getString("jamtelat2") ?: "",
    absen2 = getString("absen2") ?: ""
)
