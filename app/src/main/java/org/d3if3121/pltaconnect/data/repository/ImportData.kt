package org.d3if3121.pltaconnect.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.d3if3121.pltaconnect.data.repository.interfaces.RetrofitClient
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel


suspend fun ImportData(url: String): String{
    return withContext(Dispatchers.IO){
        try {
            val response = RetrofitClient.instance.runScript(url)
            "Import Sukses"
        } catch (e: Exception){
            "Import Gagal : $e"
        }
    }
}

