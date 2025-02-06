package org.d3if3121.tellink.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.MahasiswaEdit
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.data.repository.interfaces.AddMahasiswaResponse
import org.d3if3121.tellink.data.repository.interfaces.DeleteMahasiswaResponse
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListResponse
import org.d3if3121.tellink.data.repository.interfaces.UpdateMahasiswaResponse

class MahasiswaListRepository (
    private val mahasiswaRef: CollectionReference
): MahasiswaListInterface {
    override fun getMahasiswaList() = callbackFlow {
        val listener = mahasiswaRef
            .orderBy("nama")
            .addSnapshotListener { snapshot, e ->
                val mahasiswaListResponse =
                    if (snapshot != null){
                        val mahasiswaList = snapshot.map {
                            it.toMahasiswa()
                        }
                        Response.Success(mahasiswaList)
                    } else {
                        Response.Failure(e)
                    }
                trySend(mahasiswaListResponse)
            }
        awaitClose{
            listener.remove()
        }
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
        val mahasiswaSama = mahasiswaRef.whereEqualTo("nim", mahasiswa.nim).get().await()

        if (mahasiswaSama.isEmpty){
            val id = mahasiswaRef.add(mahasiswa).await().id
            Response.Success(id)
        } else {
            Response.Failure(Exception("NIM already registered."))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun markProject(nim: String, projectId: List<String>) {
        val mahasiswaQuery = mahasiswaRef
            .whereEqualTo("nim", nim)

        mahasiswaQuery.get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {

                    val mahasiswaDoc = snapshot.documents.first()

                    // Update viewedProjects dengan menambahkan semua projectId yang diberikan
                    mahasiswaDoc.reference.update("viewedProjects", FieldValue.arrayUnion(*projectId.toTypedArray()))
                        .addOnSuccessListener {
                            Log.d("Firestore", "Project(s) $projectId marked as viewed for $nim")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Failed to mark project as viewed", e)
                        }
                } else {
                    Log.e("Firestore", "No mahasiswa found with nim $nim")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to fetch mahasiswa", e)
            }
    }


    override suspend fun updateMahasiswa(mahasiswa: MahasiswaEdit) = try {
        val querySnapshot = mahasiswaRef.whereEqualTo("nim", mahasiswa.nim).get().await()

        if(!querySnapshot.isEmpty){
            val mahasiswaDocument = querySnapshot.documents.first()
            mahasiswaDocument.reference.update(
                mapOf(
                    Mahasiswa.NAMA to mahasiswa.nama,
                    Mahasiswa.JURUSAN to mahasiswa.major,
                )
            ).await()
            Response.Success("Edit Success.")
        } else{
            Response.Failure(Exception("Edit Failed."))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun checkRequestProject(id: String, nim: String): Boolean {
        return try {
            val process = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
            val requests = process.get("requests") as? List<String>
            requests?.contains(id) ?: false
        } catch (e: Exception) {
            false
        }
    }


    override suspend fun deleteMahasiswa(id: String) = try {
        val process = mahasiswaRef.document(id).delete().await()
        Response.Success(process)
    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun getMahasiswaByNim(nim: String): Mahasiswa {
        val mahasiswa = mahasiswaRef.whereEqualTo("nim", nim).get().await()
        if (!mahasiswa.isEmpty){
            return mahasiswa.first().toMahasiswa()
        } else {
            return Mahasiswa()
        }
    }

//    override suspend fun getMahasiswaByNim3(nim: String): Mahasiswa? {
//        return try {
//            val document = mahasiswaRef.document(nim).get().await()
//            if (document.exists()) {
//                document.toMahasiswa()
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }

    override suspend fun loginMahasiswa(nim: String, password: String) = try {
        val docmahasiswa = mahasiswaRef.whereEqualTo("nim", nim).get().await()
        if (!docmahasiswa.isEmpty){
            val mahasiswa = docmahasiswa.first().toMahasiswa()

            if (mahasiswa.password == password){
                Response.Success(mahasiswa)
            } else {
                Response.Failure(Exception("Incorrect Password."))
            }
        } else {
            Response.Failure(Exception("NIM doesn't exist."))
        }
    } catch (e: Exception){
        Response.Failure(e)
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