package org.d3if3121.tellink.data.repository.interfaces

import kotlinx.coroutines.flow.Flow
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response


typealias ProjectListResponse = Response<List<Project>>
typealias ProjectListByNimResponse = Response<List<Project>>
typealias ProjectListUserResponse = Response<List<Project>>
typealias AddProjectResponse = Response<String>
typealias AddRequestResponse = Response<String>
typealias AddAcceptResponse = Response<String>
typealias DeleteAcceptResponse = Response<String>
typealias DeleteRequestResponse = Response<String>
typealias UpdateProjectResponse = Response<String>
typealias DeleteProjectResponse = Response<Void>

interface ProjectListInterface {
    fun getProjectList(): Flow<ProjectListResponse>
    fun getProjectListByNim(nim: String): Flow<ProjectListByNimResponse>
    fun getProjectListUser(id: String): Flow<ProjectListUserResponse>
    fun getRequestList(nim: String): Flow<ProjectListUserResponse>

    suspend fun addProject(project: Project): AddProjectResponse
    suspend fun updateProject(projectId: String, project: Project): UpdateProjectResponse
    suspend fun deleteProject(id: String): DeleteProjectResponse

    suspend fun getProjectById(nim: String): Project

    suspend fun addRequest(projectId: String, nim: String): AddRequestResponse
    suspend fun addAccept(projectId: String, nim: String): AddAcceptResponse
    suspend fun deleteAccept(projectId: String, nim: String): DeleteAcceptResponse
    suspend fun deleteRequest(projectId: String, nim: String): DeleteRequestResponse

}