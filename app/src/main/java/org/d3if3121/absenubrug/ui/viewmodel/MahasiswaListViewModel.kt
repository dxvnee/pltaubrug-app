package org.d3if3121.absenubrug.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3121.absenubrug.data.datastore.UserPreferences
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.Jam
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.MahasiswaLogin
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.data.repository.interfaces.AddMahasiswaResponse
import org.d3if3121.absenubrug.data.repository.interfaces.AddAbsenResponse
import org.d3if3121.absenubrug.data.repository.interfaces.LoginResponse
import org.d3if3121.absenubrug.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.absenubrug.data.repository.interfaces.AbsenListResponse
import org.d3if3121.absenubrug.data.repository.interfaces.AddFotoProfil
import org.d3if3121.absenubrug.data.repository.interfaces.EditMahasiswaResponse
import org.d3if3121.absenubrug.data.repository.interfaces.GetJamResponse
import org.d3if3121.absenubrug.data.repository.interfaces.GetMahasiswaResponse
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MahasiswaListViewModel @Inject constructor(
    private val repo: MahasiswaListInterface,
    private val userPreferences: UserPreferences
): ViewModel() {

    val userId: StateFlow<String?> = userPreferences.userid
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    var absenListResponse by mutableStateOf<AbsenListResponse>(Response.Loading)
        private set

    var addFotoProfil by mutableStateOf<AddFotoProfil>(Response.Loading)
        private set
    var absenList by mutableStateOf<List<Absen>>(emptyList())
        private set

    var addMahasiswaResponse by mutableStateOf<AddMahasiswaResponse>(Response.Loading)
        private set
    var editMahasiswaResponse by mutableStateOf<EditMahasiswaResponse>(Response.Loading)
        private set
    var getMahasiswaResponse by mutableStateOf<GetMahasiswaResponse>(Response.Loading)
        private set

    var getJamResponse by mutableStateOf<GetJamResponse>(Response.Loading)
        private set


    var addAbsenResponse by mutableStateOf<AddAbsenResponse>(Response.Loading)
        private set

    var loginResponse by mutableStateOf<LoginResponse>(Response.Loading)
        private set

    var user by mutableStateOf(Mahasiswa())
        private set

    var jam by mutableStateOf(Jam())
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


    init {
        getMahasiswaFromDatastore()
    }

    fun getMahasiswaFromDatastore() = viewModelScope.launch {
        userId.collectLatest { id ->
            if (id != null) { getMahasiswa(id) }
        }
    }

    fun getAbsenList(user: Mahasiswa) = viewModelScope.launch {
        repo.getAbsenList(user).collect() {
            absenListResponse = it
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
    fun editMahasiswa(mahasiswa: Mahasiswa) = viewModelScope.launch {
        changeLoading(true)
        editMahasiswaResponse = repo.editMahasiswa(mahasiswa)
    }
    fun getMahasiswa(nip: String) = viewModelScope.launch {
        repo.getMahasiswa(nip).collect{
            getMahasiswaResponse = it
            if(it is Response.Success){
                user = it.data ?: Mahasiswa()
            } else if(it is Response.Loading){
                changeLoading(true)
            }
        }
    }

    fun getJam() = viewModelScope.launch {
        repo.getJam().collect{
            getJamResponse = it
            if(it is Response.Success){
                jam = it.data ?: Jam()
                Log.d("jamupdate2", jam.toString())

            }
        }
    }


    fun login(userId: String) {
        viewModelScope.launch {
            userPreferences.saveuser(userId)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearuser()
        }
    }



    fun addAbsen(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        Log.d("ekkk", "0")

        addAbsenResponse = repo.addAbsen(absen)
    }
    fun addAbsenPulang(absen: Absen) = viewModelScope.launch {
        changeLoading(true)
        Log.d("ekkk", "0")

        addAbsenResponse = repo.addAbsenPulang(absen)
    }

    fun addAbsenResponseReset() {
        addAbsenResponse = Response.Loading
    }

    fun addMahasiswaResponseReset() {
        addMahasiswaResponse = Response.Loading
    }


    fun getMahasiswaResponseReset() {
        getMahasiswaResponse = Response.Loading
    }


    fun editMahasiswaResponseReset() {
        editMahasiswaResponse = Response.Loading
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