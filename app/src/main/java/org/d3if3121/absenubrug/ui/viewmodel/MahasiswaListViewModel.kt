package org.d3if3121.absenubrug.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.MahasiswaEdit
import org.d3if3121.absenubrug.data.model.MahasiswaLogin
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.data.repository.interfaces.AddMahasiswaResponse
import org.d3if3121.absenubrug.data.repository.interfaces.AddAbsenResponse
import org.d3if3121.absenubrug.data.repository.interfaces.LoginResponse
import org.d3if3121.absenubrug.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.absenubrug.data.repository.interfaces.AbsenListResponse
import org.d3if3121.absenubrug.data.repository.interfaces.AddFotoProfil
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MahasiswaListViewModel @Inject constructor(
    private val repo: MahasiswaListInterface
): ViewModel() {
    var absenListResponse by mutableStateOf<AbsenListResponse>(Response.Loading)
        private set

    var addFotoProfil by mutableStateOf<AddFotoProfil>(Response.Loading)
        private set
    var absenList by mutableStateOf<List<Absen>>(emptyList())
        private set

    var addMahasiswaResponse by mutableStateOf<AddMahasiswaResponse>(Response.Loading)
        private set
    var addAbsenResponse by mutableStateOf<AddAbsenResponse>(Response.Loading)
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
        repo.getAbsenList(user).collect() {
            absenListResponse = it
            Log.d("hew", it.toString())

        }
    }

    fun addUser(mahasiswa: Mahasiswa) {
        user = mahasiswa
    }
    fun changeLoading(input: Boolean){
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

    fun changeAbsen(input: Absen){
        currentAbsen = input
    }

    fun changeList(absen: List<Absen>){
        absenList = absen
    }

    fun addFotoProfil(nip: String, uri: Uri) = viewModelScope.launch {
        changeLoading(true)

        addFotoProfil = repo.addFotoProfil(nip, uri)
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
        repo.addAbsen(Absen(tanggal = "5 July 2004", nip = mahasiswa.nip))
    }

    fun addAbsen(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        addAbsenResponse = repo.addAbsen(absen)
    }
    fun addAbsenPulang(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        addAbsenResponse = repo.addAbsenPulang(absen)
    }

    fun addAbsenResponseReset() {
        addAbsenResponse = Response.Loading
    }

    fun addMahasiswaResponseReset() {
        addMahasiswaResponse = Response.Loading
    }


    fun loginResponseReset() {
        loginResponse = Response.Loading
        user = Mahasiswa()
    }
    fun addFotoProfilReset() {
        addFotoProfil = Response.Loading
    }


    fun loginMahasiswa(response: MahasiswaLogin) = viewModelScope.launch {
        changeLoading(true)
        loginResponse = repo.loginMahasiswa(response.nim, response.password)
    }




}