package org.d3if3121.absenubrug.data.repository.interfaces

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.MahasiswaEdit
import org.d3if3121.absenubrug.data.model.Response


typealias AbsenListResponse = Response<List<Absen>>
typealias AddMahasiswaResponse = Response<String>
typealias AddUserResponse = Response<Mahasiswa>
typealias LoginResponse = Response<Mahasiswa>

typealias AddAbsenResponse = Response<String>
typealias AddFotoProfil = Response<String>


interface MahasiswaListInterface {
    fun getAbsenList(user: Mahasiswa): Flow<AbsenListResponse>

    suspend fun addMahasiswa(mahasiswa: Mahasiswa): AddMahasiswaResponse
    fun addUser(mahasiswa: Mahasiswa): Flow<AddUserResponse>
    suspend fun addAbsen(absen: Absen): AddAbsenResponse
    suspend fun addAbsenPulang(absen: Absen): AddAbsenResponse
    suspend fun loginMahasiswa(nim: String, password: String): LoginResponse
    suspend fun addFotoProfil(nip: String, uri: Uri): AddFotoProfil



}