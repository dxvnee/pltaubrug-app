package org.d3if3121.pltaconnect.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.repository.interfaces.RetrofitClient
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel

@Composable
fun ImportData(viewmodel: PegawaiListViewModel, url: String): String {
    val coroutineScope = rememberCoroutineScope()
    var responseText by remember { mutableStateOf("Menunggu respons...") }


    Log.e("IMPORT", url)

    fun importData(): String {
        coroutineScope.launch {
            try {
                Log.e("IMPORT", "JALAN")
                val response = RetrofitClient.instance.runScript(url)
                responseText = "Import sukses!"
                Log.d("IMPORT", responseText)

            } catch (e: Exception) {
                responseText = "Error: ${e.localizedMessage}"
                Log.d("IMPORT", responseText)
            }
        }
        return responseText
    }
    LaunchedEffect(Unit){
        importData()
    }
    return responseText
}

