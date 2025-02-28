package org.d3if3121.absenubrugadmin.ui.component

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import org.d3if3121.absenubrugadmin.components.LoadingIndicator
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel

@Composable
fun DialogGambar(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    imageUrl: String,
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
            AsyncImage(
                model = imageUrl,
                contentDescription = "Bukti Hadir",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(350.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
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
            confirmButton = {

            },
            containerColor = Warna.PutihNormal,
            shape = RoundedCornerShape(20.dp)
        )
    }

}


@Composable
fun DialogRole(
    viewmodel: MahasiswaListViewModel,
    onDismissRequest: () -> Unit,
    dialogrole: Boolean,
    pegawai: Mahasiswa,
){
    var selectedrole by remember { mutableStateOf("") }
    val context = LocalContext.current

    if(dialogrole){
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = "Pilih Role (${pegawai.nama})", color = Warna.MerahNormal, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column{
                    if(selectedrole != ""){
                        Text(
                            text = "Role dipilih: $selectedrole",
                            color = Warna.MerahNormal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                    ButtonTiga(
                        onClick1 = { selectedrole = "PEGAWAI"},
                        onClick2 = { selectedrole = "SHEET"},
                        onClick3 = { selectedrole = "ADMIN"},
                        warna1 = Warna.Hijau,
                        warna2 = Warna.Kuning,
                        warna3 = Warna.Merah,
                        text1 = "Pegawai",
                        text2 = "Sheet",
                        text3 = "Admin",
                        modifier = Modifier.weight(1f),
                    )

                    Text(text = when(selectedrole){
                        "PEGAWAI" -> { "Role yang dapat akses aplikasi Absensi." }
                        "SHEET" -> { "Role yang dapat akses aplikasi Sheet dan Absensi." }
                        "ADMIN" -> { "Role yang dapat akses aplikasi Admin." }
                        else -> { "Pilih Role terlebih dahulu!" }
                    },
                        color = Warna.MerahNormal, fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)

                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        viewmodel.editRole(
                            Mahasiswa(
                                nama = pegawai.nama,
                                nip = pegawai.nip,
                                role = pegawai.role.minus(pegawai.role.toSet()).plus(selectedrole)
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier.fillMaxWidth().padding(end = 5.dp)
                ) {
                    Text(text = "Edit", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

            },
            containerColor = Warna.PutihNormal,
            shape = RoundedCornerShape(20.dp)
        )
    }

}