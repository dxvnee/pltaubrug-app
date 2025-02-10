package org.d3if3121.pltaconnect.data.repository.interfaces

import kotlinx.coroutines.flow.Flow
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.PegawaiEdit
import org.d3if3121.pltaconnect.data.model.Response


typealias PegawaiListResponse = Response<List<Pegawai>>
typealias AddPegawaiResponse = Response<String>
typealias AddUserResponse = Response<Pegawai>
typealias UpdatePegawaiResponse = Response<String>
typealias DeletePegawaiResponse = Response<Void>
typealias PegawaiByNimResponse = Response<Pegawai>

typealias LoginResponse = Response<Pegawai>

interface PegawaiListInterface {
    fun getPegawaiList(): Flow<PegawaiListResponse>

    suspend fun addPegawai(pegawai: Pegawai): AddPegawaiResponse
    fun addUser(pegawai: Pegawai): Flow<AddUserResponse>

    suspend fun updatePegawai(pegawai: PegawaiEdit): UpdatePegawaiResponse
    suspend fun deletePegawai(id: String): DeletePegawaiResponse

    suspend fun loginPegawai(nim: String, password: String): LoginResponse

    suspend fun getPegawaiByNim(nim: String): Pegawai
    suspend fun checkRequestProject(id: String, nim: String): Boolean
    suspend fun markProject(nim: String, projectId: List<String>)


}