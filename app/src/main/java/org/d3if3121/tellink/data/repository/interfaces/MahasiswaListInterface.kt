package org.d3if3121.tellink.data.repository.interfaces

import kotlinx.coroutines.flow.Flow
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.MahasiswaEdit
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Response


typealias MahasiswaListResponse = Response<List<Mahasiswa>>
typealias AddMahasiswaResponse = Response<String>
typealias AddUserResponse = Response<Mahasiswa>
typealias UpdateMahasiswaResponse = Response<String>
typealias DeleteMahasiswaResponse = Response<Void>
typealias MahasiswaByNimResponse = Response<Mahasiswa>

typealias LoginResponse = Response<Mahasiswa>

interface MahasiswaListInterface {
    fun getMahasiswaList(): Flow<MahasiswaListResponse>

    suspend fun addMahasiswa(mahasiswa: Mahasiswa): AddMahasiswaResponse
    fun addUser(mahasiswa: Mahasiswa): Flow<AddUserResponse>

    suspend fun updateMahasiswa(mahasiswa: MahasiswaEdit): UpdateMahasiswaResponse
    suspend fun deleteMahasiswa(id: String): DeleteMahasiswaResponse

    suspend fun loginMahasiswa(nim: String, password: String): LoginResponse

    suspend fun getMahasiswaByNim(nim: String): Mahasiswa
    suspend fun checkRequestProject(id: String, nim: String): Boolean
    suspend fun markProject(nim: String, projectId: List<String>)


}