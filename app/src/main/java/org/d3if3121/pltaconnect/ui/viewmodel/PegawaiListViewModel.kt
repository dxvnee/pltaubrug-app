package org.d3if3121.pltaconnect.ui.viewmodel

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.PegawaiEdit
import org.d3if3121.pltaconnect.data.model.PegawaiLogin
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.data.model.data.Debit
import org.d3if3121.pltaconnect.data.repository.getFirebaseToken
import org.d3if3121.pltaconnect.data.repository.interfaces.AddDebitResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.AddPegawaiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.DeletePegawaiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiByNimResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.LoginResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListInterface
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.UpdatePegawaiResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PegawaiListViewModel @Inject constructor(
    private val repo: PegawaiListInterface
): ViewModel() {


    var tanggal by mutableStateOf<String>("")
        private set
    var addPegawaiResponse by mutableStateOf<AddPegawaiResponse>(Response.Loading)
        private set

    var pegawaiListResponse by mutableStateOf<PegawaiListResponse>(Response.Loading)
        private set

    var updatePegawaiResponse by mutableStateOf<UpdatePegawaiResponse>(Response.Loading)
        private set
    var deletePegawaiResponse by mutableStateOf<DeletePegawaiResponse>(Response.Loading)
        private set

    var pegawaiByNimResponse by mutableStateOf<PegawaiByNimResponse>(Response.Loading)
        private set

    var loginResponse by mutableStateOf<LoginResponse>(Response.Loading)
        private set

    var user by mutableStateOf(Pegawai())
        private set

    var pegawai by mutableStateOf(Pegawai())
        private set

    var pegawaiMap by mutableStateOf(mapOf<String, Pegawai>())
        private set

    var pegawaiMapProfile by mutableStateOf(mapOf<String, Pegawai>())
        private set

    var pegawaiProfile by mutableStateOf(Pegawai())
        private set

    var viewedProjects by mutableStateOf(listOf<String>())
        private set


    fun addViewedProject(projectId: String) {
        viewedProjects = viewedProjects + projectId // Membuat salinan baru dari List dengan menambah proyek
    }



    init {
        getPegawaiList()
    }

    private fun getPegawaiList() = viewModelScope.launch {
        repo.getPegawaiList().collect() {
            pegawaiListResponse = it
        }
    }

    fun changeTanggal(selectedDate: String){
        tanggal = selectedDate
    }


    fun addPegawai(pegawai: Pegawai) = viewModelScope.launch {
        addPegawaiResponse = repo.addPegawai(pegawai)
    }


    fun addUser(pegawai: Pegawai) = viewModelScope.launch {
        repo.addUser(pegawai).collect { response ->
            when (val pegawairesponse = response) {
                is Response.Success -> {
                    user = pegawairesponse.data!!
                    Log.d("CURRENTUSER", "User updated: ${user}")
                }
                is Response.Failure -> {
                    Log.e("CURRENTUSER", "Error: ${pegawairesponse.e}")
                }
                Response.Loading -> TODO()
            }
        }
    }

    fun getPegawaiByNim(nim: String) = viewModelScope.launch {
        if (!pegawaiMap.containsKey(nim)) {
            val result = repo.getPegawaiByNim(nim)
            pegawaiMap = pegawaiMap + (nim to result)
        }
        pegawai = pegawaiMap[nim] ?: Pegawai()
    }

    suspend fun getPegawaiByNimSuspend(nim: String): Pegawai {
        return withContext(Dispatchers.IO) {
            if (!pegawaiMap.containsKey(nim)) {
                val result = repo.getPegawaiByNim(nim)
                pegawaiMap = pegawaiMap + (nim to result)
            }
            // Mengembalikan pegawai dari map atau pegawai kosong jika tidak ditemukan
            pegawaiMap[nim] ?: Pegawai()
        }
    }

    fun getPegawaiByNimProfile(nim: String) = viewModelScope.launch {
        if (!pegawaiMap.containsKey(nim)) {
            val result = repo.getPegawaiByNim(nim)
            pegawaiMapProfile = pegawaiMapProfile + (nim to result)
        }
        pegawaiProfile = pegawaiMapProfile[nim] ?: Pegawai()
    }

    fun markProject(nim: String) = viewModelScope.launch {
        repo.markProject(nim, viewedProjects)
    }





    fun editUser(nama: String, jurusan: String){
        user = user.copy(
            nama = nama,
            jurusan = jurusan
        )
        updatePegawaiResponse = Response.Loading
    }

    fun loginPegawai(response: PegawaiLogin) = viewModelScope.launch {
        loginResponse = repo.loginPegawai(response.nim, response.password)
    }

    fun updatePegawai(pegawai: PegawaiEdit) = viewModelScope.launch {
        updatePegawaiResponse = repo.updatePegawai(pegawai)

    }
    fun deletePegawai(id: String) = viewModelScope.launch {
        deletePegawaiResponse = repo.deletePegawai(id)
    }

    //DEBIT

    var addDebitResponse by mutableStateOf<AddDebitResponse>(Response.Loading)
        private set

    fun addDebit(debit: Debit) = viewModelScope.launch {
        addDebitResponse = repo.addDebit(debit)
    }
    fun addDebitResponseReset() {
        addDebitResponse = Response.Loading
    }




}