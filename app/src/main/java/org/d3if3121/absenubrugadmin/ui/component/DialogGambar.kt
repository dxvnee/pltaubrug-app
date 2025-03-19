package org.d3if3121.absenubrugadmin.ui.component

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
            ButtonCommon(text = "Tutup"){ onClick() }
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
                ButtonCommon(text = "Lanjutkan"){ onClick1() }
            } else {
                ButtonCommon(text = "Kembali"){ onClick2() }
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


@Composable
fun DialogRole(
    viewmodel: MahasiswaListViewModel,
    onDismissRequest: () -> Unit,
    dialogrole: Boolean,
    pegawai: Mahasiswa,
    cekAction: (Mahasiswa) -> Unit
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
                            fontWeight = FontWeight.Normal,
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

                    if("UNKNOWN" !in pegawai.role){
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    viewmodel.editRole(
                                        Mahasiswa(
                                            nama = pegawai.nama,
                                            nip = pegawai.nip,
                                            role = pegawai.role.minus(pegawai.role.toSet()).plus("UNKNOWN")
                                        )
                                    )
                                },
                            horizontalArrangement = Arrangement.End,
                        ){
                            Text(
                                text = "Reset Role",
                                color = Warna.MerahNormal,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                    Text(text = when(selectedrole){
                        "PEGAWAI" -> { "Role yang dapat akses aplikasi Absensi." }
                        "SHEET" -> { "Role yang dapat akses aplikasi Sheet dan Absensi." }
                        "ADMIN" -> { "Role yang dapat akses aplikasi Admin." }
                        else -> { "Pilih Role terlebih dahulu!" }
                    },
                        color = Warna.MerahNormal, fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                        textAlign = TextAlign.Center)

                }

            },
            confirmButton = {
                Row {
                    ButtonCommon(text = "Edit", modifier = Modifier
                        .weight(1f).padding(end = 3.dp)
                    ){
                        if(selectedrole != ""){
                            viewmodel.editRole(
                                Mahasiswa(
                                    nama = pegawai.nama,
                                    nip = pegawai.nip,
                                    role = pegawai.role.minus(pegawai.role.toSet()).plus(selectedrole)
                                )
                            )
                        }

                    }

                    if("UNKNOWN" !in pegawai.role) {
                        ButtonCommon(
                            text = "Cek Absen",
                            modifier = Modifier.weight(1f).padding(start = 3.dp),
                        ){
                            cekAction(pegawai)
                        }
                    }
                }


            },
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
) {
    var nama by remember { mutableStateOf(viewmodel.user.nama) }
    var posisi by remember { mutableStateOf(viewmodel.user.posisi) }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = onDismissRequest,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Edit Data Profil",
                        color = Warna.MerahNormal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

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
                    Text(
                        text = "KIRIM",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            containerColor = Warna.PutihNormal,
            shape = RoundedCornerShape(20.dp)
        )
    }
}
