package org.d3if3121.absenubrug.ui.component

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.d3if3121.absenubrug.components.LoadingIndicator
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.ui.theme.Warna
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel

@Composable
fun DialogGambar(
    onDismissRequest: () -> Unit,
    imageUri: Uri?,
    imageUrl: String?,
    onClick: () -> Unit,
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Foto Terpilih", color = Warna.MerahNormal, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            if (imageUrl != ""){
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Bukti Hadir",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(350.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Gambar",
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

        },
        confirmButton = {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Tutup", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Warna.PutihNormal,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun DialogLokasi(
    konfirmasilokasi: Boolean,
    onDismissRequest: () -> Unit,
    lokasi: String,
    onClick1: () -> Unit,
    onClick2: () -> Unit,

){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Lokasi Terpilih", color = Warna.MerahNormal, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column{
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = lokasi, color = Warna.MerahNormal, fontSize = 16.sp, fontWeight = FontWeight.Normal)

                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){

                    if(konfirmasilokasi){
                        Text(text = "Anda berada dalam wilayah kerja!", color = Warna.Hijau, fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    } else {
                        Text(text = "Anda tidak berada dalam wilayah kerja!", color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                    }
                }

            }

        },
        confirmButton = {
            if (konfirmasilokasi) {
                Button(
                    onClick = onClick1,
                    colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Lanjutkan", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onClick2,
                    colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Kembali", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

        },
        containerColor = Warna.PutihNormal,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun DialogLoading(
    viewmodel: MahasiswaListViewModel,
    action: () -> Unit = {}
){
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
                        Button(
                            onClick = {
                                showRetry = false
                                viewmodel.changeLoading(false)
                                action()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                            shape = RoundedCornerShape(7.dp),
                            modifier = Modifier.fillMaxWidth().offset(y= 20.dp)
                        ) {
                            Text(text = "Coba Lagi", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    } else{
                        LoadingIndicator()
                    }
                }

            },
            confirmButton = {},
            containerColor = Warna.PutihNormal,
            shape = RoundedCornerShape(20.dp)
        )
    }

}

@Composable
fun DialogEditProfile(
    viewmodel: MahasiswaListViewModel,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
){
    var nama by remember { mutableStateOf(viewmodel.user.nama) }
    var posisi by remember { mutableStateOf(viewmodel.user.posisi) }

    if (showDialog){
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = onDismissRequest,
            title = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = "Edit Data Profil", color = Warna.MerahNormal, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Nama:",
                        color = Warna.MerahNormal,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    InputPutih(
                        input = nama,
                        placeholder = "Masukkan nama anda...",
                        onInputChange = { input ->
                            nama = input
                        },
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Posisi:",
                        color = Warna.MerahNormal,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    InputPutih(
                        input = posisi,
                        placeholder = "Masukkan posisi anda...",
                        onInputChange = { input ->
                            posisi = input
                        },
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        viewmodel.editMahasiswa(
                            Mahasiswa(
                                nip = viewmodel.user.nip,
                                nama = nama,
                                posisi = posisi
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "KIRIM", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Warna.PutihNormal,
            shape = RoundedCornerShape(20.dp)
        )
    }

}