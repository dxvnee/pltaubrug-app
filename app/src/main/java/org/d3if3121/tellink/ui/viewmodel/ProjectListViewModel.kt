package org.d3if3121.tellink.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.data.repository.interfaces.AddProjectResponse
import org.d3if3121.tellink.data.repository.interfaces.AddRequestResponse
import org.d3if3121.tellink.data.repository.interfaces.AddAcceptResponse
import org.d3if3121.tellink.data.repository.interfaces.DeleteProjectResponse
import org.d3if3121.tellink.data.repository.interfaces.DeleteAcceptResponse
import org.d3if3121.tellink.data.repository.interfaces.DeleteRequestResponse
import org.d3if3121.tellink.data.repository.interfaces.LoginResponse
import org.d3if3121.tellink.data.repository.interfaces.ProjectListInterface
import org.d3if3121.tellink.data.repository.interfaces.ProjectListResponse
import org.d3if3121.tellink.data.repository.interfaces.ProjectListUserResponse
import org.d3if3121.tellink.data.repository.interfaces.ProjectListByNimResponse
import org.d3if3121.tellink.data.repository.interfaces.UpdateProjectResponse
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val repo: ProjectListInterface,
    private val cloudinary: ProjectListInterface
): ViewModel() {
    var projectListResponse by mutableStateOf<ProjectListResponse>(Response.Loading)
        private set

    var projectListUserResponse by mutableStateOf<ProjectListUserResponse>(Response.Loading)
        private set

    var projectListByNimResponse by mutableStateOf<ProjectListByNimResponse>(Response.Loading)
        private set




    var addProjectResponse by mutableStateOf<AddProjectResponse>(Response.Loading)
        private set
    var addRequestResponse by mutableStateOf<AddRequestResponse>(Response.Loading)
        private set
    var addAcceptResponse by mutableStateOf<AddAcceptResponse>(Response.Loading)
        private set

    var deleteAcceptResponse by mutableStateOf<DeleteAcceptResponse>(Response.Loading)
        private set

    var updateProjectResponse by mutableStateOf<UpdateProjectResponse>(Response.Loading)
        private set
    var deleteRequestResponse by mutableStateOf<DeleteRequestResponse>(Response.Loading)
        private set

    var deleteProjectResponse by mutableStateOf<DeleteProjectResponse>(Response.Loading)
        private set

    var project by mutableStateOf<Project>(Project())
        private set

    var projectMap by mutableStateOf(mapOf<String, Project>())
        private set


    init {
        getProjectList()
    }

    fun getProjectList() = viewModelScope.launch {
        repo.getProjectList().collect() {
            projectListResponse = it
        }
    }
    fun getProjectListUser(nim: String) = viewModelScope.launch {
        repo.getProjectListUser(nim).collect() {
            projectListUserResponse = it
        }
    }

    fun getProjectListByNim(nim: String) = viewModelScope.launch {
        repo.getProjectListByNim(nim).collect() {
            projectListByNimResponse = it
        }
    }







    fun getProjectById(id: String) =  viewModelScope.launch {
        if (!projectMap.containsKey(id)) {
            val result = repo.getProjectById(id)
            Log.d("idproject", result.toString())

            projectMap = projectMap + (id to result)
        }
        project = projectMap[id] ?: Project()
    }

    suspend fun getProjectByIdSuspend(id: String): Project {
        return withContext(Dispatchers.IO) {
            if (!projectMap.containsKey(id)) {
                val result = repo.getProjectById(id)
                Log.d("idproject", result.toString())

                projectMap = projectMap + (id to result)
            }

            projectMap[id] ?: Project()
        }
    }


    fun editUser(nama: String, jurusan: String){
//        user = user.copy(
//            nama = nama,
//            jurusan = jurusan
//        )
        updateProjectResponse = Response.Loading
    }

    fun addProject(project: Project) = viewModelScope.launch {
        addProjectResponse = repo.addProject(project)
    }

    fun addRequest(projectId: String, nim: String) = viewModelScope.launch {
        addRequestResponse = repo.addRequest(projectId, nim)
    }
    fun addAccept(projectId: String, nim: String) = viewModelScope.launch {
        addAcceptResponse = repo.addAccept(projectId, nim)
    }

    fun deleteAccept(projectId: String, nim: String) = viewModelScope.launch {
        deleteAcceptResponse = repo.deleteAccept(projectId, nim)
    }

    fun deleteRequest(projectId: String, nim: String) = viewModelScope.launch {
        deleteRequestResponse = repo.deleteRequest(projectId, nim)
    }

    fun resetAddProjectResponse() {
        addProjectResponse = Response.Loading
    }
    fun resetAddAcceptResponse() {
        addAcceptResponse = Response.Loading
    }
    fun resetDeleteAcceptResponse() {
        deleteAcceptResponse = Response.Loading
    }

    fun resetAddRequestResponse() {
        addRequestResponse = Response.Loading
    }
    fun resetDeleteRequestResponse() {
        deleteRequestResponse = Response.Loading
    }

    fun updateProject(projectId: String, project: Project) = viewModelScope.launch {
        updateProjectResponse = repo.updateProject(projectId, project)

    }

    fun deleteProject(id: String) = viewModelScope.launch {
        deleteProjectResponse = repo.deleteProject(id)
    }







}