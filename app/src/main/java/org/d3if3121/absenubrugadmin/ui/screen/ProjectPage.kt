package org.d3if3121.absenubrugadmin.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
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
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.absenubrugadmin.ui.component.BottomBar
import org.d3if3121.absenubrugadmin.ui.component.InputPutih
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrugadmin.R
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Response
import org.d3if3121.absenubrugadmin.navigation.Screen
import org.d3if3121.absenubrugadmin.ui.component.BuktiHadir
import org.d3if3121.absenubrugadmin.ui.component.ButtonTiga
import org.d3if3121.absenubrugadmin.ui.component.DataDua
import org.d3if3121.absenubrugadmin.ui.component.DialogLoading
import org.d3if3121.absenubrugadmin.ui.component.HeaderContent
import org.d3if3121.absenubrugadmin.ui.component.TextKeterangan
import org.d3if3121.absenubrugadmin.ui.formula.getFileNameFromUri
import org.d3if3121.absenubrugadmin.ui.formula.isLebihCepat
import org.d3if3121.absenubrugadmin.ui.formula.selisihJam


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

            Box(
                modifier = Modifier.background(color = Warna.PutihNormal).fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding() - 70.dp,
                        )
                ) {
                    MainContentProject(
                        lazyListState = lazyListState,
                        paddingValues = paddingValues,
                        viewmodel = viewmodel,
                        navController = navController
                    )
                }


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

    val context = LocalContext.current

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
        },
        judul = "Absensi"
    )

    when(masukpergi){
        "(Masuk)" -> {
            val jamsebelum by remember { mutableStateOf("07:00") }
            ProjectTambah(
                viewmodel = viewmodel,
                jamsebelum = jamsebelum,
                currentJam = viewmodel.currentAbsen.jam,
                currentKeterangan = viewmodel.currentAbsen.keterangan,
                currentDeskripsi = viewmodel.currentAbsen.deskripsi,
                telat = { jamSebelum, jamSesudah ->
                    isLebihCepat(jamSebelum, jamSesudah)
                },
                jamtelat = { jamSebelum, jamSesudah ->
                    selisihJam(jamSebelum, jamSesudah)
                },
                viewmodeledit = { absen, ispulang -> viewmodel.editAbsenPulang(absen, ispulang) },
                viewmodeldelete = { viewmodel.deleteAbsenPulang(it) },
                isPulang = false
            )
        }
        "(Pulang)" -> {
            val jamsebelum by remember { mutableStateOf("17:00") }

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
                    telat = { jamSebelum, jamSesudah ->
                        isLebihCepat(jamSesudah, jamSebelum)
                    },
                    jamtelat = { jamSebelum, jamSesudah ->
                        selisihJam(jamSesudah, jamSebelum)
                    },
                    viewmodeledit = { absen, ispulang -> viewmodel.editAbsenPulang(absen, ispulang) },
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
    telat: (String, String) -> Boolean,
    jamtelat: (String, String) -> String,
    viewmodeledit: (Absen, Boolean) -> Unit,
    viewmodeldelete: (Absen) -> Unit,
    isPulang: Boolean,
){

    val context = LocalContext.current
    val currentAbsen by remember { mutableStateOf(viewmodel.currentAbsen) }
    var absen by remember { mutableStateOf("") }
    val jamsebelum by remember { mutableStateOf(jamsebelum) }
    var jamsesudah by remember { mutableStateOf(currentJam) }
    var selectedStatus by remember { mutableStateOf(currentKeterangan) }
    var deskripsi by remember { mutableStateOf(currentDeskripsi) }
    val belumabsenmasuk by remember { mutableStateOf(currentKeterangan == "Belum Absen") }


    var telat = remember { derivedStateOf { telat(jamsebelum,jamsesudah)  } }
    val jamtelat = remember { derivedStateOf { jamtelat(jamsebelum, jamsesudah) } }
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
                    telat = telat.value,
                    jamtelat = jamtelat.value,
                    selectedStatus = selectedStatus,
                    absenpulang = isPulang
                )
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
                )



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
                sudahabsen = belumabsenmasuk
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
                        konfirmasikirim = deskripsi != "" && selectedStatus != "Pilih Keterangan"

                        if(konfirmasikirim){
                            var absensi =  Absen(
                                nama = currentAbsen.nama,
                                nip = currentAbsen.nip,
                                tanggal = currentAbsen.tanggal,
                                keterangan = selectedStatus,
                                deskripsi = deskripsi,
                                jam = jamsesudah,
                                telat = if(telat.value) "TELAT" else "TIDAK TELAT",
                                jamtelat = jamtelat.value,
                            )

                            viewmodeledit(absensi, isPulang)
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
                        var absensi = Absen(
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
            viewmodel.editAbsenResponseReset()
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
            viewmodel.deleteAbsenPulangReset()
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

