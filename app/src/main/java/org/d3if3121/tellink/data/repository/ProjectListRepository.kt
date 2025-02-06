package org.d3if3121.tellink.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.MahasiswaEdit
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.data.repository.interfaces.AddMahasiswaResponse
import org.d3if3121.tellink.data.repository.interfaces.AddRequestResponse
import org.d3if3121.tellink.data.repository.interfaces.DeleteMahasiswaResponse
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListResponse
import org.d3if3121.tellink.data.repository.interfaces.ProjectListInterface
import org.d3if3121.tellink.data.repository.interfaces.UpdateMahasiswaResponse
import java.util.Date

class ProjectListRepository (
    private val projectRef: CollectionReference,
    private val mahasiswaRef: CollectionReference,
    @ApplicationContext private val context: Context
): ProjectListInterface {

    override fun getProjectList() = callbackFlow {
        val listener = projectRef
            .orderBy("title")
            .addSnapshotListener { snapshot, e ->
                val projectListResponse =
                    if (snapshot != null){
                        val projectList = snapshot.map { document ->
                            val projectData = document.toProject()

                            val imageUrl = document.getString("image") ?: ""
                            projectData.copy(image = imageUrl)
                        }
                        Response.Success(projectList)
                    } else {
                        Response.Failure(e)
                    }
                trySend(projectListResponse)
            }
        awaitClose{
            listener.remove()
        }
    }

    override fun getRequestList(nim: String) = callbackFlow {
        val listener = mahasiswaRef.whereEqualTo("nim", nim)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Response.Failure(e)).isSuccess
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val projectList = mutableListOf<Project>()
                    val tasks = snapshot.documents.map { document ->
                        val requestId = document.getString("request") ?: return@map
                        projectRef.document(requestId).get()
                            .addOnSuccessListener { projectDoc ->
                                val projectData = projectDoc.toProject()
                                val updatedProject = projectData.copy(image = document.getString("image") ?: "")
                                projectList.add(updatedProject)
                                if (projectList.size == snapshot.documents.size) {
                                    trySend(Response.Success(projectList)).isSuccess
                                }
                            }
                            .addOnFailureListener { exception ->
                                trySend(Response.Failure(exception)).isSuccess
                            }
                    }
                } else {
                    trySend(Response.Success(emptyList<Project>())).isSuccess
                }
            }

        awaitClose {
            listener.remove()
        }
    }




    override fun getProjectListByNim(nim: String) = callbackFlow {
        val listener = projectRef
            .orderBy("title")
            .addSnapshotListener { snapshot, e ->
                val projectListResponse =
                    if (snapshot != null) {
                        val projectList = snapshot.map { document ->
                            val projectData = document.toProject()

                            val ownerUserId = document.getString("nim")
                            if (ownerUserId == nim) {
                                val imageUrl = document.getString("image") ?: ""
                                projectData.copy(image = imageUrl)
                            } else {
                                null
                            }
                        }.filterNotNull()

                        Response.Success(projectList)
                    } else {
                        Response.Failure(e)
                    }

                trySend(projectListResponse)
            }

        awaitClose {
            listener.remove()
        }
    }





    override suspend fun addRequest(projectId: String, nim: String) = try {
        val process = projectRef.document(projectId)

        process.update("requests", FieldValue.arrayUnion(nim))
            .await()

        val process2 = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
        process2.reference.update("requests", FieldValue.arrayUnion(projectId)).await()

        Response.Success(projectId)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addAccept(projectId: String, nim: String) = try {
        val process = projectRef.document(projectId)

        process.update("accept", FieldValue.arrayUnion(nim))
            .await()

        val process2 = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
        process2.reference.update("accept", FieldValue.arrayUnion(projectId)).await()

        //

        val process3 = projectRef.document(projectId)

        process3.update("requests", FieldValue.arrayRemove(nim))
            .await()

        val process4 = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
        process4.reference.update("requests", FieldValue.arrayRemove(projectId)).await()


        Response.Success(projectId)
    } catch (e: Exception) {
        Response.Failure(e)
    }
    override suspend fun deleteAccept(projectId: String, nim: String) = try {
        val process = projectRef.document(projectId)

        process.update("accept", FieldValue.arrayRemove(nim))
            .await()

        val process2 = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
        process2.reference.update("accept", FieldValue.arrayRemove(projectId)).await()

        //

        val process3 = projectRef.document(projectId)

        process3.update("requests", FieldValue.arrayUnion(nim))
            .await()

        val process4 = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
        process4.reference.update("requests", FieldValue.arrayUnion(projectId)).await()

        Response.Success(projectId)
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override suspend fun deleteRequest(projectId: String, nim: String) = try {
        val process3 = projectRef.document(projectId)

        process3.update("requests", FieldValue.arrayRemove(nim))
            .await()

        val process4 = mahasiswaRef.whereEqualTo("nim", nim).get().await().documents.first()
        process4.reference.update("requests", FieldValue.arrayRemove(projectId)).await()

        Response.Success(projectId)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun getProjectListUser(nim: String) = callbackFlow {
        val mahasiswaQuery = mahasiswaRef.whereEqualTo("nim", nim)
        var projectListener: ListenerRegistration? = null
        var viewedProjects: List<String> = emptyList()

        val mahasiswaListener = mahasiswaQuery.addSnapshotListener { mahasiswaSnapshot, mahasiswaError ->
            if (mahasiswaSnapshot != null && !mahasiswaSnapshot.isEmpty) {
                viewedProjects = mahasiswaSnapshot.documents.firstOrNull()?.get("viewedProjects") as? List<String> ?: emptyList()

                projectListener = projectRef
                    .orderBy("title")
                    .addSnapshotListener { projectSnapshot, projectError ->
                        if (projectSnapshot != null) {

                            val projectList = projectSnapshot.map { document ->
                                val projectData = document.toProject()


                                val projectOwnerNim = document.getString("nim") ?: ""


                                if (projectOwnerNim != nim && projectData.id !in viewedProjects) {
                                    val imageUrl = document.getString("image") ?: ""
                                    projectData.copy(image = imageUrl)
                                } else {
                                    null
                                }
                            }.filterNotNull()


                            trySend(Response.Success(projectList))
                        } else {
                            trySend(Response.Failure(projectError))
                        }
                    }
            } else {
                trySend(Response.Failure(mahasiswaError))
            }
        }


        awaitClose {
            mahasiswaListener.remove()
            projectListener?.remove()
        }
    }







    override suspend fun addProject(project: Project) = try {

        val id = projectRef.add(project).await().id

        if (project.imageupload != null){
            val imageUrl = uploadImagetoFirebase(project.imageupload.uri, id)
            projectRef.document(id).update("image", imageUrl).await()
        }
        Response.Success(id)

    } catch (e: Exception){
        Response.Failure(e)
    }

    override suspend fun updateProject(projectId: String, project: Project) = try {
        val projectDocument = projectRef.document(projectId)

        projectDocument.update(
            mapOf(
                Project.TITLE to project.title,
                Project.DESC to project.desc,
                Project.TAG to project.tag,
            )
        ).await()

        Response.Success("Edit successful.")
    } catch (e: Exception){
        Response.Failure(Exception("No project found for the given NIM."))
    }



    override suspend fun deleteProject(id: String) = try {
        val process = projectRef.document(id).delete().await()
        val querySnapshot = mahasiswaRef.whereArrayContains("requests", id).get().await()

        for (document in querySnapshot.documents) {
            document.reference.update(
                "requests", FieldValue.arrayRemove(id)
            ).await()
        }

        Response.Success(process)
    } catch (e: Exception){
        Response.Failure(e)
    }


    override suspend fun getProjectById(id: String): Project {
        try {
            val project = projectRef.document(id).get().await()
            if (project.exists()){
                return project.toProject()
            } else {
                return Project()
            }
        } catch (e: Exception) {
            return Project()
        }
    }

    suspend fun getImageUrlFromFirebase(projectId: String): String? {
        return try {
            val storage = FirebaseStorage.getInstance()
            val storageReference: StorageReference = storage.reference.child("images/$projectId")
            val uri = storageReference.downloadUrl.await()
            uri.toString()
        } catch (e: Exception) {
            null
        }
    }


    suspend fun uploadImagetoFirebase(uri: Uri, id: String): String {

        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child("images/" + id)
        val uploadTask = storageReference.putFile(uri)


        return try {
            uploadTask.await()
            storageReference.downloadUrl.await().toString()
        } catch (e: Exception) {
            ""
        }
    }

//    override suspend fun getProjectByNim3(nim: String): Project? {
//        return try {
//            val document = projectRef.document(nim).get().await()
//            if (document.exists()) {
//                document.toProject()
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }

}

fun DocumentSnapshot.toProject() = Project(
    id = this.id,
    nim = getString(Project.NIM) ?: "Default",
    title = getString(Project.TITLE) ?: "Default",
    desc = getString(Project.DESC)?: "DefaultName",
    tag = get(Project.TAG) as? List<String> ?: emptyList(),
    date =  getString(Project.DATE) ?: "",
    image = getString(Project.IMAGE) ?: "",
    requests = get(Project.REQUESTS) as? List<String> ?: emptyList(),
    accept = get(Project.ACCEPT) as? List<String> ?: emptyList(),
)