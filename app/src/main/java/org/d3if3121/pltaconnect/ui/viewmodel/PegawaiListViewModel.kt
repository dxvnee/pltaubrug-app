package org.d3if3121.pltaconnect.ui.viewmodel

import android.net.Uri
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
import org.d3if3121.pltaconnect.data.datastore.UserPreferences
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.PegawaiEdit
import org.d3if3121.pltaconnect.data.model.PegawaiLogin
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.data.model.data.Debit
import org.d3if3121.pltaconnect.data.model.data.MasukRequest
import org.d3if3121.pltaconnect.data.model.data.PemakaianRequest
import org.d3if3121.pltaconnect.data.model.data.Produksi
import org.d3if3121.pltaconnect.data.model.data.ProduksiRequest
import org.d3if3121.pltaconnect.data.repository.interfaces.AddDebitResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.AddMasukResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.SheetResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.AddProduksiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.AddPemakaianResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.AddPegawaiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.DeletePegawaiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.GetDebitResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.GetMasukResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.GetPemakaianResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.GetProduksiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiByNimResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.LoginResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListInterface
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.UpdatePegawaiResponse
import org.d3if3121.pltaconnect.data.repository.interfaces.AddFotoProfil
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PegawaiListViewModel @Inject constructor(
    private val repo: PegawaiListInterface
): ViewModel() {

    var loading by mutableStateOf(false)
        private set

    var tanggal by mutableStateOf<String>("")
        private set

    var tanggalBefore by mutableStateOf<String>("")
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

    var addFotoProfil by mutableStateOf<AddFotoProfil>(Response.Loading)
        private set

    var usermasuk by mutableStateOf(false)
        private set

    fun addViewedProject(projectId: String) {
        viewedProjects = viewedProjects + projectId // Membuat salinan baru dari List dengan menambah proyek
    }

    init {
        getPegawaiList()
    }

    private fun getPegawaiList() = viewModelScope.launch {
        changeLoading(true)
        repo.getPegawaiList().collect() {
            pegawaiListResponse = it
        }
    }

    fun changeLoading(input: Boolean){
        Log.d("keganti", input.toString())
        loading = input
    }

    fun changeTanggal(selectedDate: String){
        tanggal = selectedDate
    }

    fun changeTanggalBefore(selectedDate: String){
        tanggalBefore = selectedDate
    }

//    fun login(userId: String) {
//        viewModelScope.launch {
//            userPreferences.saveuser(userId)
//            usermasuk = true
//        }
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            userPreferences.clearuser()
//            usermasuk = false
//        }
//    }


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


    fun loginPegawai(response: PegawaiLogin) = viewModelScope.launch {
        changeLoading(true)
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
    var addProduksiResponse by mutableStateOf<AddProduksiResponse>(Response.Loading)
        private set
    var addPemakaianResponse by mutableStateOf<AddPemakaianResponse>(Response.Loading)
        private set
    var addMasukResponse by mutableStateOf<AddMasukResponse>(Response.Loading)
        private set

    //
    var getDebitResponse by mutableStateOf<GetDebitResponse>(Response.Loading)
        private set
    var getProduksiResponse by mutableStateOf<GetProduksiResponse>(Response.Loading)
        private set
    var getProduksiBeforeResponse by mutableStateOf<GetProduksiResponse>(Response.Loading)
        private set
    var getPemakaianResponse by mutableStateOf<GetPemakaianResponse>(Response.Loading)
        private set
    var getPemakaianBeforeResponse by mutableStateOf<GetPemakaianResponse>(Response.Loading)
        private set
    var getMasukResponse by mutableStateOf<GetMasukResponse>(Response.Loading)
        private set

    //

    var getSheetResponse by mutableStateOf<SheetResponse>(Response.Loading)
        private set


    fun addDebit(debit: Debit) = viewModelScope.launch {
        changeLoading(true)
        addDebitResponse = repo.addDebit(debit)
    }
    fun addProduksi(produksiRequest: ProduksiRequest) = viewModelScope.launch {
        changeLoading(true)
        addProduksiResponse = repo.addProduksi(produksiRequest)
    }
    fun addPemakaian(pemakaianRequest: PemakaianRequest) = viewModelScope.launch {
        changeLoading(true)
        addPemakaianResponse = repo.addPemakaian(pemakaianRequest)
    }
    fun addMasuk(masukRequest: MasukRequest) = viewModelScope.launch {
        changeLoading(true)
        addMasukResponse = repo.addMasuk(masukRequest)
    }


    fun getDebit(id: String) = viewModelScope.launch {
        changeLoading(true)
        getDebitResponse = repo.getDebit(id)
    }

    fun getProduksi(id: String) = viewModelScope.launch {
        changeLoading(true)
        getProduksiResponse = repo.getProduksi(id)
    }
    fun getProduksiBefore(id: String) = viewModelScope.launch {
        changeLoading(true)
        getProduksiBeforeResponse = repo.getProduksi(id)
    }

    fun getPemakaian(id: String) = viewModelScope.launch {
        changeLoading(true)
        getPemakaianResponse = repo.getPemakaian(id)
    }
    fun getPemakaianBefore(id: String) = viewModelScope.launch {
        changeLoading(true)
        getPemakaianBeforeResponse = repo.getPemakaian(id)
    }
    fun getMasuk(id: String) = viewModelScope.launch {
        changeLoading(true)
        getMasukResponse = repo.getMasuk(id)
    }

    fun getMasukReset() {
        getMasukResponse = Response.Loading
    }
    fun getPemakaianBeforeReset() {
        getPemakaianBeforeResponse = Response.Loading
    }
    fun getPemakaianReset() {
        getPemakaianResponse = Response.Loading
    }
    fun getProduksiBeforeReset() {
        getProduksiBeforeResponse = Response.Loading
    }
    fun getProduksiReset() {
        getProduksiResponse = Response.Loading
    }







    fun getSheet(id: String) = viewModelScope.launch {
        changeLoading(true)
        getSheetResponse = repo.getSheet(id)
    }

    //
    fun addDebitResponseReset() {
        addDebitResponse = Response.Loading
    }
    fun addProduksiResponseReset() {
        addProduksiResponse = Response.Loading
    }
    fun addPemakaianResponseReset() {
        addPemakaianResponse = Response.Loading
    }
    fun addMasukResponseReset() {
        addMasukResponse = Response.Loading
    }
    //
    fun getSheetResponseReset() {
        getSheetResponse = Response.Loading
    }

    fun addFotoProfil(nip: String, uri: Uri) = viewModelScope.launch {
        changeLoading(true)

        addFotoProfil = repo.addFotoProfil(nip, uri)
    }

    fun loginResponseReset() {
        loginResponse = Response.Loading
        user = Pegawai()
    }
    fun addFotoProfilReset() {
        addFotoProfil = Response.Loading
    }





}