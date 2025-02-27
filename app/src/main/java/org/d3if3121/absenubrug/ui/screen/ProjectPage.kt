package org.d3if3121.absenubrug.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrug.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Job
import org.d3if3121.absenubrug.ui.component.BottomBar
import org.d3if3121.absenubrug.ui.component.InputPutih
import org.d3if3121.absenubrug.ui.component.TopBar
import org.d3if3121.absenubrug.ui.component.cekScroll
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrug.R
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.ImageUpload
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.navigation.Screen
import org.d3if3121.absenubrug.ui.component.BuktiHadir
import org.d3if3121.absenubrug.ui.component.ButtonKecil
import org.d3if3121.absenubrug.ui.component.ButtonTiga
import org.d3if3121.absenubrug.ui.component.DataDua
import org.d3if3121.absenubrug.ui.component.DialogGambar
import org.d3if3121.absenubrug.ui.component.DialogLoading
import org.d3if3121.absenubrug.ui.component.DialogLokasi
import org.d3if3121.absenubrug.ui.component.HeaderContent
import org.d3if3121.absenubrug.ui.component.TextKeterangan
import org.d3if3121.absenubrug.ui.formula.createAbsen
import org.d3if3121.absenubrug.ui.formula.dapatJam
import org.d3if3121.absenubrug.ui.formula.getFileNameFromUri
import org.d3if3121.absenubrug.ui.formula.isLebihCepat
import org.d3if3121.absenubrug.ui.formula.selisihJam
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectPage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel,
) {
    val lazyListState = rememberLazyListState()
    val user = viewmodel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                MainContentProject(
                    lazyListState = lazyListState,
                    paddingValues = paddingValues,
                    viewmodel = viewmodel,
                    navController = navController
                )
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )

}


@Composable
fun MainContentProject(
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    navController: NavHostController
) {

    var context = LocalContext.current

    AbsenResponse(context, viewmodel, navController)

    DialogLoading(viewmodel)



    Column(
        modifier = Modifier
            .padding(start = 17.dp, end = 17.dp)
            .background(color = Warna.PutihNormal)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight(),
            state = lazyListState
        ) {
            item {
                ProjectContent(
                    viewmodel = viewmodel
                )

            }
        }
    }
}

@Composable
fun ProjectContent(
    viewmodel: MahasiswaListViewModel
){
    var masukpergi by remember { mutableStateOf("(Masuk)") }

    HeaderContent(
        viewmodel = viewmodel,
        masukpergi = masukpergi,
        onclick1 = {
            masukpergi = "(Masuk)"
        },
        onclick2 = {
            masukpergi = "(Pulang)"
        }
    )

    when(masukpergi){
        "(Masuk)" -> {
            var jamsebelum by remember { mutableStateOf("07:00") }
            ProjectTambah(
                viewmodel = viewmodel,
                jamsebelum = jamsebelum,
                currentJam = viewmodel.currentAbsen.jam,
                currentKeterangan = viewmodel.currentAbsen.keterangan,
                currentDeskripsi = viewmodel.currentAbsen.deskripsi,
                imagePath = viewmodel.currentAbsen.image ?: "",
                telat = { jamsebelum, jamsesudah ->
                    isLebihCepat(jamsebelum, jamsesudah)
                },
                jamtelat = { jamsebelum, jamsesudah ->
                    selisihJam(jamsebelum, jamsesudah)
                },
                viewmodeledit = { viewmodel.editAbsenPulang(it) },
                viewmodeldelete = { viewmodel.deleteAbsenPulang(it) },
                isPulang = false
            )
        }
        "(Pulang)" -> {
            var jamsebelum by remember { mutableStateOf("17:00") }

            Log.d("geg", viewmodel.currentAbsen.keterangan)
            if(viewmodel.currentAbsen.keterangan == "Belum Absen"){
                Text(
                    text = "Pegawai Belum Mengisi Absen Masuk!",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            } else {
                ProjectTambah(
                    viewmodel = viewmodel,
                    jamsebelum = jamsebelum,
                    currentJam = viewmodel.currentAbsen.jam2,
                    currentKeterangan = viewmodel.currentAbsen.keterangan2,
                    currentDeskripsi = viewmodel.currentAbsen.deskripsi2,
                    imagePath = viewmodel.currentAbsen.image ?: "",
                    telat = { jamsebelum, jamsesudah ->
                        isLebihCepat(jamsesudah, jamsebelum)
                    },
                    jamtelat = { jamsebelum, jamsesudah ->
                        selisihJam(jamsesudah, jamsebelum)
                    },
                    viewmodeledit = { viewmodel.editAbsenPulang(it) },
                    viewmodeldelete = { viewmodel.deleteAbsenPulang(it) },
                    isPulang = true
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(50.dp))
}


@Composable
fun ProjectTambah(
    viewmodel: MahasiswaListViewModel,
    jamsebelum: String,
    currentJam: String,
    currentKeterangan: String,
    currentDeskripsi: String,
    imagePath: String,
    telat: (String, String) -> Boolean,
    jamtelat: (String, String) -> String,
    viewmodeledit: (Absen) -> Unit,
    viewmodeldelete: (Absen) -> Unit,
    isPulang: Boolean,
){

    var context = LocalContext.current
    var currentAbsen by remember { mutableStateOf(viewmodel.currentAbsen) }
    var absen by remember { mutableStateOf("") }
    var jamsebelum by remember { mutableStateOf(jamsebelum) }
    var jamsesudah by remember { mutableStateOf(currentJam) }
    var selectedStatus by remember { mutableStateOf(currentKeterangan) }
    var deskripsi by remember { mutableStateOf(currentDeskripsi) }
    var gantijam by remember { mutableStateOf("") }
    var belumabsenmasuk by remember { mutableStateOf(currentKeterangan == "Belum Absen") }


    var telat by remember { mutableStateOf(telat(jamsebelum,jamsesudah)) }
    var jamtelat by remember { mutableStateOf(jamtelat(jamsebelum, jamsesudah)) }
    var imageUrl by remember { mutableStateOf(currentAbsen.image) }
    var lokasi by remember { mutableStateOf("") }

    var konfirmasikirim by remember { mutableStateOf(false) }
    var peringatan by remember { mutableStateOf(false) }



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(15.dp)
    ){
        Column(
            modifier = Modifier.padding(17.dp)
        ){
            DataDua(
                ratacond = true,
                duaratacond = true,
                judul1 = "Keterangan :",
                judul2 = "Jam Absen:",
                warnarata2 = Warna.MerahNormal,
                ratarata = jamsebelum,

                judul2k2 = "Jam Masuk:",
                ratarata2 = jamsesudah
            )

            Column {
                TextKeterangan(
                    telat = telat,
                    jamtelat = jamtelat,
                    selectedStatus = selectedStatus,
                    absenpulang = isPulang
                )

                if(belumabsenmasuk){
                    ButtonTiga(
                        onClick1 = { selectedStatus = "Hadir"},
                        onClick2 = { selectedStatus = "Izin"},
                        onClick3 = { selectedStatus = "Cuti"},
                        warna1 = Warna.Hijau,
                        warna2 = Warna.Kuning,
                        warna3 = Warna.Merah,
                        text1 = "Hadir",
                        text2 = "Izin",
                        text3 = "Tidak Hadir",
                        modifier = Modifier.weight(1f),
                        absenpulang = isPulang
                    )
                }

            }

            BuktiHadir(
                absen = currentAbsen.absen,
                imageUrl = imageUrl,
                lokasi = lokasi,
                onUrlChange = { url ->
                    imageUrl = url
                },
                onLokasiChange = { lat, lon ->
                    lokasi = "Lokasi: $lat, $lon"
                },
                onImageChange = {
                    absen = getFileNameFromUri(context, it).toString()
                },
                onClickDialogLokasi = {
                    imageUrl = null
                    absen = lokasi
                },
                sudahabsen = if (belumabsenmasuk) true else false
            )
            Text(
                text = "Ganti Jam Masuk :",
                color = Warna.MerahNormal,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            InputPutih(
                input = jamsesudah,
                placeholder = "cth. '08:15'",
                onInputChange = { input ->
                    jamsesudah = input
                },
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth().padding(bottom = 10.dp)

            )

            Text(
                text = stringResource(id = R.string.deskripsi),
                color = Warna.MerahNormal,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            InputPutih(
                expand = true,
                input = deskripsi,
                placeholder = stringResource(id = R.string.masuk_deskripsi),
                onInputChange = { input ->
                    deskripsi = input
                },
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)

            )
            if (peringatan) {
                Text(text = "Harus mengisi semua kolom!", color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Row {
                    ButtonAksi(
                        warna = Warna.MerahNormal,
                        modifier = Modifier.weight(1f).padding(end = 7.dp),
                        text = "KIRIM"
                    ){
                        konfirmasikirim = if (deskripsi != "" && selectedStatus != "Pilih Keterangan") true else false

                        if(konfirmasikirim){
                            var absensi =  Absen(
                                nama = currentAbsen.nama,
                                nip = currentAbsen.nip,
                                tanggal = currentAbsen.tanggal,
                                keterangan = selectedStatus,
                                deskripsi = deskripsi,
                                jam = jamsesudah,
                                telat = if(telat) "TELAT" else "TIDAK TELAT",
                                jamtelat = jamtelat,
                            )

                            viewmodeledit(absensi)
                            peringatan = false
                            konfirmasikirim = false
                            absen = ""
                        } else {
                            peringatan = true
                        }
                    }

                    ButtonAksi(
                        warna = Color.Red,
                        modifier = Modifier.weight(1f).padding(start = 7.dp),
                        text = "HAPUS"
                    ){
                        var absensi =  Absen(
                            nama = currentAbsen.nama,
                            nip = currentAbsen.nip,
                            tanggal = currentAbsen.tanggal,
                        )

                        viewmodeldelete(absensi)
                    }

                }

            }








        }
    }
}

@Composable
fun ButtonAksi(
    warna: Color,
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = warna),
        shape = RoundedCornerShape(7.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun AbsenResponse(context: Context, viewmodel: MahasiswaListViewModel, navController: NavHostController){
    when(val addRequestResponse = viewmodel.addAbsenResponse){
        is Response.Loading -> {

        }
        is Response.Success -> {
            Toast.makeText(context, "Berhasil Absen!", Toast.LENGTH_SHORT).show()
            viewmodel.addAbsenResponseReset()
            viewmodel.changeLoading(false)
            navController.navigate(Screen.Home.route)
        }
        is Response.Failure -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", addRequestResponse.e.toString())
            viewmodel.addAbsenResponseReset()
        }
    }

    when(val response = viewmodel.editAbsenResponse){
        is Response.Loading -> {

        }
        is Response.Success -> {
            Toast.makeText(context, "Berhasil Edit!", Toast.LENGTH_SHORT).show()
            viewmodel.addAbsenResponseReset()
            viewmodel.changeLoading(false)
            navController.navigate(Screen.Home.route)
        }
        is Response.Failure -> {
            Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", response.e.toString())
            viewmodel.addAbsenResponseReset()
        }
    }

    when(val response = viewmodel.deleteAbsenResponse){
        is Response.Loading -> {

        }
        is Response.Success -> {
            Toast.makeText(context, "Berhasil Hapus!", Toast.LENGTH_SHORT).show()
            viewmodel.addAbsenResponseReset()
            viewmodel.changeLoading(false)
            navController.navigate(Screen.Home.route)
        }
        is Response.Failure -> {
            Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", response.e.toString())
            viewmodel.addAbsenResponseReset()
        }
    }
}

@Composable
fun ImageResponse(
    viewmodel: MahasiswaListViewModel,
    onUriChange: (Uri?) -> Unit
){
    when(val response = viewmodel.fetchImageResponse){
        is Response.Success ->{
            onUriChange(response.data)
            viewmodel.changeLoading(false)
        }
        is Response.Failure -> {
            Log.e("EROR", response.e.toString())
            viewmodel.changeLoading(false)
        }
        Response.Loading -> {

        }
    }

}


