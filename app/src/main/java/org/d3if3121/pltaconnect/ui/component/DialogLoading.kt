package org.d3if3121.pltaconnect.ui.component


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import org.d3if3121.pltaconnect.components.LoadingIndicator
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel


@Composable
fun DialogLoading(
    viewmodel: PegawaiListViewModel,
    action: () -> Unit = {}
){
    Log.d("loading", viewmodel.loading.toString())
    var showRetry by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = viewmodel.loading, key2 = showRetry) {
        if (viewmodel.loading) {
            Log.d("loading", "jalan")

            delay(30000)
            if (viewmodel.loading) {
                showRetry = true
            }
        } else {
            showRetry = false
        }
    }
    if (viewmodel.loading){
        AlertDialog(
            modifier = if (showRetry) Modifier.height( 150.dp).width(450.dp) else  Modifier.size( 90.dp),
            onDismissRequest = {},
            text = {
                Column (

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){

                    if (showRetry) {
                        Text(
                            text = "Tidak dapat terhubung dengan server (Pastikan memiliki koneksi internet)",
                            color = Warna.HitamNormal,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,

                            )
                        ButtonCommon(text = "Coba Lagi", modifier = Modifier.fillMaxWidth().offset(y= 20.dp)){
                            showRetry = false
                            viewmodel.changeLoading(false)
                            action()
                        }
                    } else{
                        LoadingIndicator()
                    }
                }

            },
            confirmButton = {

            },
            containerColor = Warna.PutihNormal,
            shape = RoundedCornerShape(20.dp)
        )
    }

}