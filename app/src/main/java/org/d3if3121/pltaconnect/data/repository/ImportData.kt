package org.d3if3121.pltaconnect.data.repository

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.d3if3121.pltaconnect.data.repository.interfaces.RetrofitClient
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel

@Composable
fun ImportData(viewmodel: PegawaiListViewModel){
    val coroutineScope = rememberCoroutineScope()
    var responseText by remember { mutableStateOf("Menunggu respons...") }

    fun importData() {
        coroutineScope.launch {
            try {
                Log.e("IMPORT", "JALAN")
                val response = RetrofitClient.instance.runScript()
                responseText = if (response.done) "Import sukses!" else "Import gagal!"
                viewmodel.addDebitResponseReset()
                Log.d("IMPORT", responseText)
            } catch (e: Exception) {
                responseText = "Error: ${e.localizedMessage}"
            }
        }
    }
    LaunchedEffect(Unit){
        importData()
    }

}

