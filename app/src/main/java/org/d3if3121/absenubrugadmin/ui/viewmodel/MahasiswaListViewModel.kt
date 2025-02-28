package org.d3if3121.absenubrugadmin.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.data.model.MahasiswaLogin
import org.d3if3121.absenubrugadmin.data.model.Response
import org.d3if3121.absenubrugadmin.data.repository.interfaces.AddMahasiswaResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.AddAbsenResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.LoginResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.absenubrugadmin.data.repository.interfaces.AbsenListResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.DeleteAbsenResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.EditAbsenResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.EditRoleResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.FetchImageResponse
import org.d3if3121.absenubrugadmin.data.repository.interfaces.MahasiswaListResponse
import javax.inject.Inject

@HiltViewModel
class MahasiswaListViewModel @Inject constructor(
    private val repo: MahasiswaListInterface
): ViewModel() {

    var absenListResponse by mutableStateOf<AbsenListResponse>(Response.Loading)
        private set

    var absenList by mutableStateOf<List<Absen>?>(emptyList())
        private set

    var mahasiswaList by mutableStateOf<List<Mahasiswa>>(emptyList())
        private set

    var mahasiswaListResponse by mutableStateOf<MahasiswaListResponse>(Response.Loading)
        private set

    var fetchImageResponse by mutableStateOf<FetchImageResponse>(Response.Loading)
        private set


    var addMahasiswaResponse by mutableStateOf<AddMahasiswaResponse>(Response.Loading)
        private set
    var addAbsenResponse by mutableStateOf<AddAbsenResponse>(Response.Loading)
        private set

    var editAbsenResponse by mutableStateOf<EditAbsenResponse>(Response.Loading)
        private set

    var deleteAbsenResponse by mutableStateOf<DeleteAbsenResponse>(Response.Loading)
        private set

    var editRoleResponse by mutableStateOf<EditRoleResponse>(Response.Loading)
        private set

    var loginResponse by mutableStateOf<LoginResponse>(Response.Loading)
        private set

    var user by mutableStateOf(Mahasiswa())
        private set

    var tanggal by mutableStateOf("")
        private set

    var tanggalSeharusnya by mutableStateOf("")
        private set

    var keteranganhome by mutableStateOf("")
        private set
    var keteranganhome2 by mutableStateOf("")
        private set

    var jamhome by mutableStateOf("")
        private set
    var jamhome2 by mutableStateOf("")
        private set

    var currentAbsen by mutableStateOf(Absen())
        private set

    var loading by mutableStateOf(false)
        private set


    fun getAbsenList(user: Mahasiswa) = viewModelScope.launch {
        repo.getAbsenList(user).collect() { data ->
            val absenbaru = (data as Response.Success).data!!.toMutableList()
            absenList = absenList?.plus(absenbaru)
            Log.d("BERHASILL3", absenList.toString())

        }
    }

    fun getMahasiswaList() = viewModelScope.launch {
        changeLoading(true)

        repo.getMahasiswaList().collect(){
            mahasiswaListResponse = it

            when(val response = mahasiswaListResponse){
                is Response.Success -> {
                    Log.d("ew", response.data.toString())
                    absenListReset()
                    mahasiswaList = response.data!!
                    Log.d("ew2", mahasiswaList.toString())

                    mahasiswaList.forEach {
                        getAbsenList(it)
                    }
                    changeLoading(false)
                    mahasiswaListResponseReset()
                }
                is Response.Failure -> {}
                is Response.Loading -> {}
            }
        }

    }

    fun absenListReset(){
        absenList = emptyList()
    }
    fun addUser(mahasiswa: Mahasiswa) {
        user = mahasiswa
    }
    fun changeLoading(input: Boolean){
        Log.d("keganti", input.toString())
        loading = input
    }

    fun changeTanggal(selectedDate: String){
        tanggal = selectedDate
    }
    fun changeAbsenListResponse(){
        absenListResponse = Response.Loading
    }
    fun changeTanggalSeharusnya(selectedDate: String){
        tanggalSeharusnya = selectedDate
        Log.d("tanggalSeharusnya", tanggalSeharusnya)

    }

    fun getImage(filePath: String) = viewModelScope.launch {
        changeLoading(true)
        fetchImageResponse = repo.fetchImageFromFirebase(filePath)
    }

    fun changeAbsen(input: Absen){
        currentAbsen = input
    }

    fun changeList(absen: List<Absen>){
        absenList = absen
    }

    fun changeKeteranganHome(input: String, input2: String){
        keteranganhome = input
        keteranganhome2 = input2
    }


    fun changeJamHome(input: String, input2: String){
        jamhome = input
        jamhome2 = input2
    }


    fun addMahasiswa(mahasiswa: Mahasiswa) = viewModelScope.launch {
        addMahasiswaResponse = repo.addMahasiswa(mahasiswa)
    }

    fun addAbsen(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        addAbsenResponse = repo.addAbsen(absen)
    }
    fun addAbsenPulang(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        addAbsenResponse = repo.addAbsenPulang(absen)
    }

    fun editAbsenPulang(absen: Absen, ispulang: Boolean) = viewModelScope.launch {
        changeLoading(true)
        editAbsenResponse = repo.editAbsen(absen, ispulang)
    }

    fun editRole(pegawai: Mahasiswa) = viewModelScope.launch {
        changeLoading(true)
        editRoleResponse = repo.editRole(pegawai)

    }

    fun editRoleResponseReset(){
        editRoleResponse = Response.Loading
    }


    fun deleteAbsenPulang(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        deleteAbsenResponse = repo.deleteAbsen(absen)
    }


    fun addAbsenResponseReset() {
        addAbsenResponse = Response.Loading
    }

    fun mahasiswaListResponseReset() {
       mahasiswaListResponse = Response.Loading
    }

    fun editAbsenResponseReset() {
        editAbsenResponse = Response.Loading
    }

    fun loginResponseReset() {
        loginResponse = Response.Loading
        user = Mahasiswa()
    }

    fun loginMahasiswa(response: MahasiswaLogin) = viewModelScope.launch {
        changeLoading(true)
        loginResponse = repo.loginMahasiswa(response.nim, response.password)
    }




}