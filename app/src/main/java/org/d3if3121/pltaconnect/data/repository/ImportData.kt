package org.d3if3121.pltaconnect.data.repository

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.d3if3121.pltaconnect.data.model.ScriptRequest
import retrofit2.HttpException
import java.net.UnknownHostException
import org.d3if3121.pltaconnect.data.repository.interfaces.RetrofitClient
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel

@Composable
fun ImportData(viewmodel: PegawaiListViewModel){
    val coroutineScope = rememberCoroutineScope()
    var responseText by remember { mutableStateOf("Menunggu respons...") }
    val accessToken = "Bearer ya29.a0AXeO80TbHa-D9ndriFs78xZTIkDQ7BEs5O8Pb5NPqAkH3GRWcUoABTR3iqUThNWu508mnaic-44HxH690dYJKDEifnhVPGYLMsfL05P9HLreMGPU5d437T2MM2MVxaS40PQXgoYDKO7eHww2bPy3OL-uXmLeNmWd3EUMY1p0dQaCgYKAXcSARMSFQHGX2MiIJGP9Wa8cbTdAd_ndWi5oA0177"

    fun importData() {
        coroutineScope.launch {
            try {
                Log.e("IMPORT", "JALAN")

                val response = RetrofitClient.instance.runScript(
                    token = accessToken,
                    request = ScriptRequest(function = "importFromFirestore")
                )
                responseText = if (response.done) "Import sukses!" else "Import gagal!"
                viewmodel.addDebitResponseReset()
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
    LaunchedEffect(Unit){
        importData()
    }

}
