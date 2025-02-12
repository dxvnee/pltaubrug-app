package org.d3if3121.pltaconnect.data.repository

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import org.d3if3121.pltaconnect.data.repository.interfaces.RetrofitClient

@Composable
fun ImportData() {
    val coroutineScope = rememberCoroutineScope()
    var responseText by remember { mutableStateOf("Menunggu respons...") }
    val accessToken = "Bearer YOUR_ACCESS_TOKEN" // Gantilah dengan token terbaru

    fun importData() {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.instance.runScript(
                    token = accessToken,
                    request = mapOf("function" to "importFromFirestore")
                )
                responseText = if (response.done) "Import sukses!" else "Import gagal!"
                Log.d("IMPORT", responseText)
            } catch (e: HttpException) {
                responseText = "Error ${e.code()}: ${e.message()}"
                Log.e("IMPORT", responseText)
            } catch (e: UnknownHostException) {
                responseText = "Tidak ada koneksi internet"
                Log.e("IMPORT", responseText)
            } catch (e: Exception) {
                responseText = "Error: ${e.localizedMessage}"
                Log.e("IMPORT", responseText)
            }
        }
    }

    // Pastikan hanya dipanggil sekali
    LaunchedEffect(Unit) {
        importData()
    }
}
