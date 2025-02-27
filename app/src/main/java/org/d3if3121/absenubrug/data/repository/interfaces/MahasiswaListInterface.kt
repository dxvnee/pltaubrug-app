package org.d3if3121.absenubrug.data.repository.interfaces

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.MahasiswaEdit
import org.d3if3121.absenubrug.data.model.Response


typealias AbsenListResponse = Response<List<Absen>>
typealias MahasiswaListResponse = Response<List<Mahasiswa>>
typealias AddMahasiswaResponse = Response<String>
typealias AddUserResponse = Response<Mahasiswa>
typealias LoginResponse = Response<Mahasiswa>

typealias AddAbsenResponse = Response<String>
typealias EditAbsenResponse = Response<String>
typealias DeleteAbsenResponse = Response<String>

typealias FetchImageResponse = Response<Uri>

interface MahasiswaListInterface {
    fun getAbsenList(user: Mahasiswa): Flow<AbsenListResponse>
    suspend fun getMahasiswaList(): MahasiswaListResponse

    suspend fun addMahasiswa(mahasiswa: Mahasiswa): AddMahasiswaResponse
    fun addUser(mahasiswa: Mahasiswa): Flow<AddUserResponse>

    suspend fun addAbsen(absen: Absen): AddAbsenResponse
    suspend fun addAbsenPulang(absen: Absen): AddAbsenResponse
    suspend fun editAbsen(absen: Absen): EditAbsenResponse
    suspend fun deleteAbsen(absen: Absen): DeleteAbsenResponse

    suspend fun loginMahasiswa(nim: String, password: String): LoginResponse

    suspend fun fetchImageFromFirebase(filePath: String): FetchImageResponse


}