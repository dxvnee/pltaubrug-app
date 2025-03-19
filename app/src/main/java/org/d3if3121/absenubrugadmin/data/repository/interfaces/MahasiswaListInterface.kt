package org.d3if3121.absenubrugadmin.data.repository.interfaces

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.data.model.Response


typealias AbsenListResponse = Response<List<Absen>>
typealias MahasiswaListResponse = Response<List<Mahasiswa>>

typealias AddMahasiswaResponse = Response<String>
typealias EditMahasiswaResponse = Response<String>
typealias GetMahasiswaResponse = Response<Mahasiswa>

typealias AddUserResponse = Response<Mahasiswa>
typealias LoginResponse = Response<Mahasiswa>

typealias AddAbsenResponse = Response<String>
typealias EditAbsenResponse = Response<String>
typealias DeleteAbsenResponse = Response<String>

typealias EditRoleResponse = Response<String>
typealias FetchImageResponse = Response<Uri>
typealias AddFotoProfil = Response<String>

interface MahasiswaListInterface {
    fun getAbsenList(user: Mahasiswa): Flow<AbsenListResponse>
    fun getMahasiswaList(): Flow<MahasiswaListResponse>

    suspend fun loginMahasiswa(nim: String, password: String): LoginResponse

    fun getMahasiswa(nip: String): Flow<GetMahasiswaResponse>
    suspend fun addMahasiswa(mahasiswa: Mahasiswa): AddMahasiswaResponse
    suspend fun editMahasiswa(mahasiswa: Mahasiswa): EditMahasiswaResponse

    fun addUser(mahasiswa: Mahasiswa): Flow<AddUserResponse>

    suspend fun addAbsen(absen: Absen): AddAbsenResponse
    suspend fun addAbsenPulang(absen: Absen): AddAbsenResponse
    suspend fun editAbsen(absen: Absen, isPulang: Boolean): EditAbsenResponse
    suspend fun deleteAbsen(absen: Absen): DeleteAbsenResponse

    suspend fun editRole(pegawai: Mahasiswa): EditAbsenResponse
    suspend fun fetchImageFromFirebase(filePath: String): FetchImageResponse
    suspend fun addFotoProfil(nip: String, uri: Uri): AddFotoProfil
}