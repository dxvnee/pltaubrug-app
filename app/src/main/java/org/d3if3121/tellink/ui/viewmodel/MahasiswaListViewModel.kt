package org.d3if3121.tellink.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.MahasiswaEdit
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.data.repository.interfaces.AddMahasiswaResponse
import org.d3if3121.tellink.data.repository.interfaces.DeleteMahasiswaResponse
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaByNimResponse
import org.d3if3121.tellink.data.repository.interfaces.LoginResponse
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListResponse
import org.d3if3121.tellink.data.repository.interfaces.UpdateMahasiswaResponse
import javax.inject.Inject

@HiltViewModel
class MahasiswaListViewModel @Inject constructor(
    private val repo: MahasiswaListInterface
): ViewModel() {
    var mahasiswaListResponse by mutableStateOf<MahasiswaListResponse>(Response.Loading)
        private set
    var addMahasiswaResponse by mutableStateOf<AddMahasiswaResponse>(Response.Loading)
        private set
    var updateMahasiswaResponse by mutableStateOf<UpdateMahasiswaResponse>(Response.Loading)
        private set
    var deleteMahasiswaResponse by mutableStateOf<DeleteMahasiswaResponse>(Response.Loading)
        private set
    var mahasiswaByNimResponse by mutableStateOf<MahasiswaByNimResponse>(Response.Loading)
        private set

    var loginResponse by mutableStateOf<LoginResponse>(Response.Loading)
        private set

    var user by mutableStateOf(Mahasiswa())
        private set

    var mahasiswa by mutableStateOf(Mahasiswa())
        private set

    var mahasiswaMap by mutableStateOf(mapOf<String, Mahasiswa>())
        private set

    var mahasiswaMapProfile by mutableStateOf(mapOf<String, Mahasiswa>())
        private set

    var mahasiswaProfile by mutableStateOf(Mahasiswa())
        private set

    var viewedProjects by mutableStateOf(listOf<String>())
        private set


    fun addViewedProject(projectId: String) {
        viewedProjects = viewedProjects + projectId // Membuat salinan baru dari List dengan menambah proyek
    }



    init {
        getMahasiswaList()
    }

    private fun getMahasiswaList() = viewModelScope.launch {
        repo.getMahasiswaList().collect() {
            mahasiswaListResponse = it
        }
    }

    fun addUser(mahasiswa: Mahasiswa) = viewModelScope.launch {
        repo.addUser(mahasiswa).collect { response ->
            when (val mahasiswaresponse = response) {
                is Response.Success -> {
                    user = mahasiswaresponse.data!!
                    Log.d("CURRENTUSER", "User updated: ${user}")
                }
                is Response.Failure -> {
                    Log.e("CURRENTUSER", "Error: ${mahasiswaresponse.e}")
                }
                Response.Loading -> TODO()
            }
        }
    }

    fun getMahasiswaByNim(nim: String) = viewModelScope.launch {
        if (!mahasiswaMap.containsKey(nim)) {
            val result = repo.getMahasiswaByNim(nim)
            mahasiswaMap = mahasiswaMap + (nim to result)
        }
        mahasiswa = mahasiswaMap[nim] ?: Mahasiswa()
    }

    suspend fun getMahasiswaByNimSuspend(nim: String): Mahasiswa {
        return withContext(Dispatchers.IO) {
            if (!mahasiswaMap.containsKey(nim)) {
                val result = repo.getMahasiswaByNim(nim)
                mahasiswaMap = mahasiswaMap + (nim to result)
            }
            // Mengembalikan mahasiswa dari map atau mahasiswa kosong jika tidak ditemukan
            mahasiswaMap[nim] ?: Mahasiswa()
        }
    }

    fun getMahasiswaByNimProfile(nim: String) = viewModelScope.launch {
        if (!mahasiswaMap.containsKey(nim)) {
            val result = repo.getMahasiswaByNim(nim)
            mahasiswaMapProfile = mahasiswaMapProfile + (nim to result)
        }
        mahasiswaProfile = mahasiswaMapProfile[nim] ?: Mahasiswa()
    }

    fun markProject(nim: String) = viewModelScope.launch {
        repo.markProject(nim, viewedProjects)
    }





    fun editUser(nama: String, jurusan: String){
        user = user.copy(
            nama = nama,
            jurusan = jurusan
        )
        updateMahasiswaResponse = Response.Loading
    }

    fun addMahasiswa(mahasiswa: Mahasiswa) = viewModelScope.launch {
        addMahasiswaResponse = repo.addMahasiswa(mahasiswa)
    }

    fun loginMahasiswa(response: MahasiswaLogin) = viewModelScope.launch {
        loginResponse = repo.loginMahasiswa(response.nim, response.password)
    }

    fun updateMahasiswa(mahasiswa: MahasiswaEdit) = viewModelScope.launch {
        updateMahasiswaResponse = repo.updateMahasiswa(mahasiswa)

    }
    fun deleteMahasiswa(id: String) = viewModelScope.launch {
        deleteMahasiswaResponse = repo.deleteMahasiswa(id)
    }

    fun checkRequestProject(id: String, nim: String): Deferred<Boolean> = viewModelScope.async {
        repo.checkRequestProject(id, nim)
    }


}