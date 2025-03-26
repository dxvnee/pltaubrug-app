package org.d3if3121.absenubrug.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrug.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.absenubrug.ui.component.BottomBar
import org.d3if3121.absenubrug.ui.component.InputPutih
import org.d3if3121.absenubrug.ui.component.TopBar
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrug.R
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.ImageUpload
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.navigation.Screen
import org.d3if3121.absenubrug.ui.component.BuktiHadir
import org.d3if3121.absenubrug.ui.component.ButtonTiga
import org.d3if3121.absenubrug.ui.component.DataDua
import org.d3if3121.absenubrug.ui.component.DialogLoading
import org.d3if3121.absenubrug.ui.component.HeaderContent
import org.d3if3121.absenubrug.ui.component.TextKeterangan
import org.d3if3121.absenubrug.ui.formula.createAbsen
import org.d3if3121.absenubrug.ui.formula.dapatJam
import org.d3if3121.absenubrug.ui.formula.getFileNameFromUri
import org.d3if3121.absenubrug.ui.formula.isLebihCepat
import org.d3if3121.absenubrug.ui.formula.selisihJam



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectPage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel,
    tanggal: String = ""
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

    LaunchedEffect(Unit, viewmodel.jam){ viewmodel.getJam() }
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
            if (viewmodel.tanggalSeharusnya != viewmodel.tanggal){

                Text(
                    text = "Anda Harus Mengisi Absen Sesuai Tanggal! (${viewmodel.tanggalSeharusnya})",
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
                    currentJam = viewmodel.currentAbsen.jam,
                    currentJamTarget = viewmodel.currentAbsen.jamtarget,
                    currentKeterangan = viewmodel.currentAbsen.keterangan,
                    currentDeskripsi = viewmodel.currentAbsen.deskripsi,
                    telat = { jamsebelum, jamsesudah ->
                        isLebihCepat(jamsebelum, jamsesudah)
                    },
                    jamtelat = { jamsebelum, jamsesudah ->
                        selisihJam(jamsebelum, jamsesudah)
                    },
                    viewmodeladd = { viewmodel.addAbsen(it) },
                    isPulang = false
                )
            }

        }
        "(Pulang)" -> {
            if(viewmodel.currentAbsen.keterangan == "Belum Absen"){
                Text(
                    text = "Anda Belum Mengisi Absen Masuk!",
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
                    currentJam = viewmodel.currentAbsen.jam2,
                    currentJamTarget = viewmodel.currentAbsen.jamtarget2,
                    currentKeterangan = viewmodel.currentAbsen.keterangan2,
                    currentDeskripsi = viewmodel.currentAbsen.deskripsi2,
                    telat = { jamsebelum, jamsesudah ->
                        isLebihCepat(jamsesudah, jamsebelum)
                    },
                    jamtelat = { jamsebelum, jamsesudah ->
                        selisihJam(jamsesudah, jamsebelum)
                    },
                    viewmodeladd = { viewmodel.addAbsenPulang(it) },
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
    currentJam: String,
    currentKeterangan: String,
    currentDeskripsi: String,
    currentJamTarget: String,
    telat: (String, String) -> Boolean,
    jamtelat: (String, String) -> String,
    viewmodeladd: (Absen) -> Unit,
    isPulang: Boolean,
){

    var context = LocalContext.current
    var currentAbsen by remember { mutableStateOf(viewmodel.currentAbsen) }

    var absen by remember { mutableStateOf("") }
    var jamsebelum = if (isPulang) viewmodel.jam.keluar else viewmodel.jam.masuk
    var jamsesudah by remember { mutableStateOf(dapatJam()) }
    var selectedStatus by remember { mutableStateOf("Pilih Keterangan") }
    var deskripsi by remember { mutableStateOf("") }
    var belumabsenmasuk by remember { mutableStateOf(currentKeterangan == "Belum Absen") }

    if(!belumabsenmasuk){
        jamsesudah = currentJam
        selectedStatus = currentKeterangan
        deskripsi = currentDeskripsi
        jamsebelum = currentJamTarget
    }

    var telat by remember { mutableStateOf(telat(jamsebelum,jamsesudah)) }
    var jamtelat by remember { mutableStateOf(jamtelat(jamsebelum, jamsesudah)) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
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
                        text3 = "Cuti",
                        modifier = Modifier.weight(1f),
                        absenpulang = isPulang
                    )
                }
            }

            BuktiHadir(
                imageUri = imageUri,
                lokasi = lokasi,
                sudahabsen = belumabsenmasuk,
                selectedstatus = selectedStatus,
                statushadir = selectedStatus == "Hadir",
                onUriChange = { uri -> imageUri = uri },
                onLokasiChange = { lat, lon -> lokasi = "Lokasi: $lat, $lon" },
                onImageChange = { absen = getFileNameFromUri(context, it).toString() },
                onClickDialogLokasi = { imageUri = null; absen = lokasi },
                absen = if (belumabsenmasuk) absen else { if (isPulang) currentAbsen.absen2 else currentAbsen.absen },
                imageUrl = if (belumabsenmasuk) "" else { if (isPulang) currentAbsen.image2 else currentAbsen.image },
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
                    if (belumabsenmasuk) deskripsi = input else {}
                },
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)

            )
            if (peringatan) {
                Text(text = "Harus mengisi semua kolom!", color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }

            if(belumabsenmasuk){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {

                            if (selectedStatus == "Hadir"){
                                konfirmasikirim = if (deskripsi != "" && selectedStatus != "Pilih Keterangan" && (lokasi != "" || imageUri != null)) true else false
                            } else {
                                konfirmasikirim = if (deskripsi != "" && selectedStatus != "Pilih Keterangan") true else false
                            }

                            if(konfirmasikirim){
                                var absensi = createAbsen(
                                    user = viewmodel.user,
                                    keterangan = selectedStatus,
                                    lokasi = if (imageUri != null) "" else  lokasi,
                                    foto = if (imageUri != null) ImageUpload(uri = imageUri!!) else null,
                                    deskripsi = deskripsi,
                                    tanggal = viewmodel.tanggal,
                                    jam = jamsesudah,
                                    telat = if (telat) "TELAT" else "TIDAK TELAT",
                                    jamtelat = jamtelat,
                                    jamtarget = viewmodel.jam,
                                    absen = absen,
                                    isPulang = if (selectedStatus == "Hadir") isPulang.toString() else "bukanhadir"
                                )

                                viewmodeladd(absensi); peringatan = false; konfirmasikirim = false; absen = ""
                            } else {
                                peringatan = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "KIRIM", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}


@Composable
fun AbsenResponse(context: Context, viewmodel: MahasiswaListViewModel, navController: NavHostController){
    when(val addRequestResponse = viewmodel.addAbsenResponse){
        is Response.Loading -> {}
        is Response.Success -> {
            Toast.makeText(context, "Berhasil Absen!", Toast.LENGTH_SHORT).show()
            viewmodel.addAbsenResponseReset()
            viewmodel.changeLoading(false)
            navController.navigate(Screen.Home.route)
        }
        is Response.Failure -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            viewmodel.addAbsenResponseReset()
        }
    }

}


