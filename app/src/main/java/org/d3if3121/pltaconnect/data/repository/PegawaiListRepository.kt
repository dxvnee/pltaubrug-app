package org.d3if3121.pltaconnect.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.PegawaiEdit
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListInterface

class PegawaiListRepository (
    private val pegawaiRef: CollectionReference
): PegawaiListInterface {
    override fun getPegawaiList() = callbackFlow {
        val listener = pegawaiRef
            .orderBy("nama")
            .addSnapshotListener { snapshot, e ->
                val pegawaiListResponse =
                    if (snapshot != null){
                        val pegawaiList = snapshot.map {
                            it.toPegawai()
                        }
                        Response.Success(pegawaiList)
                    } else {
                        Response.Failure(e)
                    }
                trySend(pegawaiListResponse)
            }
        awaitClose{
            listener.remove()
        }
    }


    override fun addUser(pegawai: Pegawai) = callbackFlow {
        val listener = pegawaiRef.whereEqualTo("nim", pegawai.nim)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val updatedPegawai = snapshot.documents.first().toPegawai()
                    trySend(Response.Success(updatedPegawai))
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


    override suspend fun addPegawai(pegawai: Pegawai) = try {
        val pegawaiSama = pegawaiRef.whereEqualTo("nim", pegawai.nim).get().await()

        if (pegawaiSama.isEmpty){
            val id = pegawaiRef.add(pegawai).await().id
            Response.Success(id)
        } else {
            Response.Failure(Exception("NIM already registered."))
        }
    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun markProject(nim: String, projectId: List<String>) {
        val pegawaiQuery = pegawaiRef
            .whereEqualTo("nim", nim)

        pegawaiQuery.get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {

                    val pegawaiDoc = snapshot.documents.first()

                    // Update viewedProjects dengan menambahkan semua projectId yang diberikan
                    pegawaiDoc.reference.update("viewedProjects", FieldValue.arrayUnion(*projectId.toTypedArray()))
                        .addOnSuccessListener {
                            Log.d("Firestore", "Project(s) $projectId marked as viewed for $nim")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Failed to mark project as viewed", e)
                        }
                } else {
                    Log.e("Firestore", "No pegawai found with nim $nim")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to fetch pegawai", e)
            }
    }


    override suspend fun updatePegawai(pegawai: PegawaiEdit) = try {
        val querySnapshot = pegawaiRef.whereEqualTo("nim", pegawai.nim).get().await()

        if(!querySnapshot.isEmpty){
            val pegawaiDocument = querySnapshot.documents.first()
            pegawaiDocument.reference.update(
                mapOf(
                    Pegawai.NAMA to pegawai.nama,
                    Pegawai.JURUSAN to pegawai.major,
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
            val process = pegawaiRef.whereEqualTo("nim", nim).get().await().documents.first()
            val requests = process.get("requests") as? List<String>
            requests?.contains(id) ?: false
        } catch (e: Exception) {
            false
        }
    }


    override suspend fun deletePegawai(id: String) = try {
        val process = pegawaiRef.document(id).delete().await()
        Response.Success(process)
    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun getPegawaiByNim(nim: String): Pegawai {
        val pegawai = pegawaiRef.whereEqualTo("nim", nim).get().await()
        if (!pegawai.isEmpty){
            return pegawai.first().toPegawai()
        } else {
            return Pegawai()
        }
    }

//    override suspend fun getPegawaiByNim3(nim: String): Pegawai? {
//        return try {
//            val document = pegawaiRef.document(nim).get().await()
//            if (document.exists()) {
//                document.toPegawai()
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }

    override suspend fun loginPegawai(nim: String, password: String) = try {
        val docpegawai = pegawaiRef.whereEqualTo("nim", nim).get().await()
        if (!docpegawai.isEmpty){
            val pegawai = docpegawai.first().toPegawai()

            if (pegawai.password == password){
                Response.Success(pegawai)
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

fun DocumentSnapshot.toPegawai() = Pegawai(
    nama = getString(Pegawai.NAMA) ?: "Default",
    password = getString(Pegawai.PASSWORD) ?: "Default",
    nim = getString(Pegawai.NIM)?: "DefaultName",
    jurusan = getString(Pegawai.JURUSAN)?: "DefaultName",
    requests = get(Pegawai.REQUESTS) as? List<String> ?: emptyList(),
    accept = get(Pegawai.ACCEPT) as? List<String> ?: emptyList(),
)