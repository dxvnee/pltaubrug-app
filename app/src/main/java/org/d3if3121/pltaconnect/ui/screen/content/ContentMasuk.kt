package org.d3if3121.pltaconnect.ui.screen.content

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.R
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.model.data.Debit
import org.d3if3121.pltaconnect.data.model.data.Masuk
import org.d3if3121.pltaconnect.data.model.data.MasukRequest
import org.d3if3121.pltaconnect.data.repository.ImportData
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.component.BarisTigaText
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.DialogLoading
import org.d3if3121.pltaconnect.ui.component.InputPutih
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.component.PindahUnit
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.exp

@Composable
fun ContentMasuk(
    tanggal: String,
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    viewmodel: PegawaiListViewModel = hiltViewModel(),
    navController: NavHostController

){
    DialogLoading(viewmodel)

    val id = tanggal + "_masuk"

    LaunchedEffect(Unit) {
        viewmodel.getMasuk(id)
        viewmodel.getMasuk(id)
    }

    var unit by remember { mutableStateOf("Unit 1") }

    var unit1 by remember { mutableStateOf(Masuk())}
    var unit2 by remember { mutableStateOf(Masuk())}
    var unit3 by remember { mutableStateOf(Masuk())}

    var context = LocalContext.current

    LaunchedEffect(unit1, unit2, unit3){
        unit1 = unit1.copy(jam = rumusMasuk(unit1))
        unit2 = unit2.copy(jam = rumusMasuk(unit2))
        unit3 = unit3.copy(jam = rumusMasuk(unit3))
    }

    GetMasukResponse(
        context = context,
        viewmodel = viewmodel
    ){ data ->
        unit1 = unit1.copy(
            keterangan = data.keterangan1,
            masuk1 = data.masuk1_1,
            keluar1 = data.keluar1_1,
            masuk2 = data.masuk1_2,
            keluar2 = data.keluar1_2,
            masuk3 = data.masuk1_3,
            keluar3 = data.keluar1_3,
        )
        unit2 = unit2.copy(
            keterangan = data.keterangan2,
            masuk1 = data.masuk2_1,
            keluar1 = data.keluar2_1,
            masuk2 = data.masuk2_2,
            keluar2 = data.keluar2_2,
            masuk3 = data.masuk2_3,
            keluar3 = data.keluar2_3,
        )
        unit3 = unit3.copy(
            keterangan = data.keterangan3,
            masuk1 = data.masuk3_1,
            keluar1 = data.keluar3_1,
            masuk2 = data.masuk3_2,
            keluar2 = data.keluar3_2,
            masuk3 = data.masuk3_3,
            keluar3 = data.keluar3_3,
        )
    }

    MasukResponse(
        context = context,
        viewmodel = viewmodel,
        navController = navController
    )

    JudulUtama(
        judul1 = "Masuk Keluar Unit",
        judul2cond = false,
        tanggal = tanggal,
        onClickBack =  onClickBack,
        onClickNext =  onClickNext,
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .background(color = Warna.PutihNormal),
        state = lazyListState
    ){
        item {

            Column {
                Text(
                    text = "Pilih Unit: ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Warna.MerahNormal,
                    modifier = Modifier.padding(top = 10.dp)
                )

                ButtonTiga(
                    onClick1 = {
                        unit = "Unit 1"
                    },
                    onClick2 = {
                        unit = "Unit 2"
                    },
                    onClick3 = {
                        unit = "Unit 3"
                    }
                )

                Row (
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    PindahUnit(unit)
                }
            }

            when(unit){
                "Unit 1" ->{
                    MainContentMasuk1(
                        masuk1 = unit1.masuk1,
                        onMasukChange1 = {
                            unit1 = unit1.copy(masuk1 = it)
                            Log.d("HEHE9", unit1.masuk1)
                        },
                        masuk2 = unit1.masuk2,
                        onMasukChange2 = {
                            unit1 = unit1.copy(masuk2 = it)
                        },
                        masuk3 = unit1.masuk3,
                        onMasukChange3 = {
                            unit1 = unit1.copy(masuk3 = it)
                        },
                        keluar1 = unit1.keluar1,
                        onKeluarChange1 = {
                            unit1 = unit1.copy(keluar1 = it)
                        },
                        keluar2 = unit1.keluar2,
                        onKeluarChange2 = {
                            unit1 = unit1.copy(keluar2 = it)
                        },
                        keluar3 = unit1.keluar3,
                        onKeluarChange3 = {
                            unit1 = unit1.copy(keluar3 = it)
                        },
                        keterangan = unit1.keterangan,
                        onKeteranganChange = {
                            unit1 = unit1.copy(keterangan = it)
                        },
                        jam = unit1.jam,
                    )
                }
                "Unit 2" ->{
                    MainContentMasuk2(
                        masuk1 = unit2.masuk1,
                        onMasukChange1 = {
                            unit2 = unit2.copy(masuk1 = it)
                        },
                        masuk2 = unit2.masuk2,
                        onMasukChange2 = {
                            unit2 = unit2.copy(masuk2 = it)
                        },
                        masuk3 = unit2.masuk3,
                        onMasukChange3 = {
                            unit2 = unit2.copy(masuk3 = it)
                        },
                        keluar1 = unit2.keluar1,
                        onKeluarChange1 = {
                            unit2 = unit2.copy(keluar1 = it)
                        },
                        keluar2 = unit2.keluar2,
                        onKeluarChange2 = {
                            unit2 = unit2.copy(keluar2 = it)
                        },
                        keluar3 = unit2.keluar3,
                        onKeluarChange3 = {
                            unit2 = unit2.copy(keluar3 = it)
                        },
                        keterangan = unit2.keterangan,
                        onKeteranganChange = {
                            unit2 = unit2.copy(keterangan = it)
                        },
                        jam = unit2.jam,
                    )
                }
                "Unit 3" ->{
                    MainContentMasuk3(
                        masuk1 = unit3.masuk1,
                        onMasukChange1 = {
                            unit3 = unit3.copy(masuk1 = it)
                        },
                        masuk2 = unit3.masuk2,
                        onMasukChange2 = {
                            unit3 = unit3.copy(masuk2 = it)
                        },
                        masuk3 = unit3.masuk3,
                        onMasukChange3 = {
                            unit3 = unit3.copy(masuk3 = it)
                        },
                        keluar1 = unit3.keluar1,
                        onKeluarChange1 = {
                            unit3 = unit3.copy(keluar1 = it)
                        },
                        keluar2 = unit3.keluar2,
                        onKeluarChange2 = {
                            unit3 = unit3.copy(keluar2 = it)
                        },
                        keluar3 = unit3.keluar3,
                        onKeluarChange3 = {
                            unit3 = unit3.copy(keluar3 = it)
                        },
                        keterangan = unit3.keterangan,
                        onKeteranganChange = {
                            unit3 = unit3.copy(keterangan = it)
                        },
                        jam = unit3.jam,
                    )
                }
            }

            ButtonMerah(
                onClick = {
                    val id = tanggal + "_masuk"
                    viewmodel.addMasuk(
                        MasukRequest(
                            id = id,
                            jam1 = unit1.jam,
                            masuk1_1 = unit1.masuk1,
                            masuk1_2 = unit1.masuk2,
                            masuk1_3 = unit1.masuk3,
                            keluar1_1 = unit1.keluar1,
                            keluar1_2 = unit1.keluar2,
                            keluar1_3 = unit1.keluar3,

                            keterangan1 = unit1.keterangan,

                            jam2 = unit2.jam,
                            masuk2_1 = unit2.masuk1,
                            masuk2_2 = unit2.masuk2,
                            masuk2_3 = unit2.masuk3,
                            keluar2_1 = unit2.keluar1,
                            keluar2_2 = unit2.keluar2,
                            keluar2_3 = unit2.keluar3,

                            keterangan2 = unit2.keterangan,

                            jam3 = unit3.jam,
                            masuk3_1 = unit3.masuk1,
                            masuk3_2 = unit3.masuk2,
                            masuk3_3 = unit3.masuk3,
                            keluar3_1 = unit3.keluar1,
                            keluar3_2 = unit3.keluar2,
                            keluar3_3 = unit3.keluar3,

                            keterangan3 = unit3.keterangan,
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(top = 18.dp).height(46.dp),
                content = {
                    Text(
                        text = "KIRIM",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = Warna.PutihNormal
                    )
                }
            )
            Spacer(modifier = Modifier.height(150.dp))

        }

    }
}


@Composable
fun MainContentMasuk1(
    masuk1: String,
    onMasukChange1: (String) -> Unit,
    keluar1: String,
    onKeluarChange1: (String) -> Unit,
    masuk2: String,
    onMasukChange2: (String) -> Unit,
    keluar2: String,
    onKeluarChange2: (String) -> Unit,
    masuk3: String,
    onMasukChange3: (String) -> Unit,
    keluar3: String,
    onKeluarChange3: (String) -> Unit,
    keterangan: String,
    onKeteranganChange: (String) -> Unit,
    jam: String,
){
    MainContent(
        masuk1 = masuk1,
        onMasukChange1 = onMasukChange1,
        masuk2 = masuk2,
        onMasukChange2 = onMasukChange2,
        masuk3 = masuk3,
        onMasukChange3 = onMasukChange3,
        keluar1 = keluar1,
        onKeluarChange1 = onKeluarChange1,
        keluar2 = keluar2,
        onKeluarChange2 = onKeluarChange2,
        keluar3 = keluar3,
        onKeluarChange3 = onKeluarChange3,
        keterangan = keterangan,
        onKeteranganChange= onKeteranganChange,
        jam = jam,
    )

}


@Composable
fun MainContentMasuk2(
    masuk1: String,
    onMasukChange1: (String) -> Unit,
    keluar1: String,
    onKeluarChange1: (String) -> Unit,
    masuk2: String,
    onMasukChange2: (String) -> Unit,
    keluar2: String,
    onKeluarChange2: (String) -> Unit,
    masuk3: String,
    onMasukChange3: (String) -> Unit,
    keluar3: String,
    onKeluarChange3: (String) -> Unit,
    keterangan: String,
    onKeteranganChange: (String) -> Unit,
    jam: String,
){
    MainContent(
        masuk1 = masuk1,
        onMasukChange1 = onMasukChange1,
        masuk2 = masuk2,
        onMasukChange2 = onMasukChange2,
        masuk3 = masuk3,
        onMasukChange3 = onMasukChange3,
        keluar1 = keluar1,
        onKeluarChange1 = onKeluarChange1,
        keluar2 = keluar2,
        onKeluarChange2 = onKeluarChange2,
        keluar3 = keluar3,
        onKeluarChange3 = onKeluarChange3,
        keterangan = keterangan,
        onKeteranganChange= onKeteranganChange,
        jam = jam,
    )

}

@Composable
fun MainContentMasuk3(
    masuk1: String,
    onMasukChange1: (String) -> Unit,
    keluar1: String,
    onKeluarChange1: (String) -> Unit,
    masuk2: String,
    onMasukChange2: (String) -> Unit,
    keluar2: String,
    onKeluarChange2: (String) -> Unit,
    masuk3: String,
    onMasukChange3: (String) -> Unit,
    keluar3: String,
    onKeluarChange3: (String) -> Unit,
    keterangan: String,
    onKeteranganChange: (String) -> Unit,
    jam: String,
){
    MainContent(
        masuk1 = masuk1,
        onMasukChange1 = onMasukChange1,
        masuk2 = masuk2,
        onMasukChange2 = onMasukChange2,
        masuk3 = masuk3,
        onMasukChange3 = onMasukChange3,
        keluar1 = keluar1,
        onKeluarChange1 = onKeluarChange1,
        keluar2 = keluar2,
        onKeluarChange2 = onKeluarChange2,
        keluar3 = keluar3,
        onKeluarChange3 = onKeluarChange3,
        keterangan = keterangan,
        onKeteranganChange= onKeteranganChange,
        jam = jam,
    )

}

@Composable
fun MainContent(
    masuk1: String,
    onMasukChange1: (String) -> Unit,
    keluar1: String,
    onKeluarChange1: (String) -> Unit,
    masuk2: String,
    onMasukChange2: (String) -> Unit,
    keluar2: String,
    onKeluarChange2: (String) -> Unit,
    masuk3: String,
    onMasukChange3: (String) -> Unit,
    keluar3: String,
    onKeluarChange3: (String) -> Unit,
    keterangan: String,
    onKeteranganChange: (String) -> Unit,
    jam: String,
){
    Card(
        modifier = Modifier
            .fillMaxWidth().height(440.dp),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){

        Column (
            modifier = Modifier.padding(17.dp).fillMaxWidth()
        ){
            DataDua(
                ratacond = true,
                judul1 = "Jam:",
                judul2 = "Jam Kerja:",
                warnarata2 = Warna.MerahTua,
                ratarata = jam,
                text1k1 = "Masuk:",
                text2k1 = "#1",
                text1k2 = "Keluar:",
                text2k2 = "#1",

                hasil1 = masuk1,
                onHasil1Change = onMasukChange1,
                hasil2 = keluar1,
                onHasil2Change = onKeluarChange1,
                besartext2 = 12
            )
            DataDua(
                judul1cond = false,
                judul2 = "Jam Kerja:",
                warnarata2 = Warna.MerahTua,
                text1k1 = "Masuk:",
                text2k1 = "#2",
                text1k2 = "Keluar:",
                text2k2 = "#2",

                hasil1 = masuk2,
                onHasil1Change = onMasukChange2,
                hasil2 = keluar2,
                onHasil2Change = onKeluarChange2,
                besartext2 = 12
            )
            DataDua(
                judul1cond = false,
                judul2 = "Jam Kerja:",
                warnarata2 = Warna.MerahTua,
                text1k1 = "Masuk:",
                text2k1 = "#3",
                text1k2 = "Keluar:",
                text2k2 = "#3",

                hasil1 = masuk3,
                onHasil1Change = onMasukChange3,
                hasil2 = keluar3,
                onHasil2Change = onKeluarChange3,
                besartext2 = 12
            )
        }
    }

    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ){
            Text(
                text = "Keterangan",
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                color = Warna.MerahNormal,
                modifier = Modifier.padding(top = 14.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth().height(230.dp),
            colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ){
            Column (
                modifier = Modifier.padding(17.dp)
            ){
                Text(
                    text = stringResource(id = R.string.nim),
                    color = Warna.MerahNormal,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                InputPutih(
                    input = keterangan,
                    placeholder = stringResource(id = R.string.enter_nim),
                    onInputChange = onKeteranganChange,
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    expand = true

                )
            }
        }
    }
}

fun isValidTimeFormat(jam: String): Boolean {
    val regex = Regex("^\\d{2}:\\d{2}$")
    return regex.matches(jam)
}

fun parseJam(jam: String): LocalTime? {
    return if (isValidTimeFormat(jam)) {
        try {
            LocalTime.parse(jam, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}

fun rumusMasuk(unit: Masuk): String {
    Log.d("HEHE4", unit.masuk1 + " " + unit.keluar1)

    val selisih1 = selisihJam(unit.masuk1, unit.keluar1)
    val selisih2 = selisihJam(unit.masuk2, unit.keluar2)
    val selisih3 = selisihJam(unit.masuk3, unit.keluar3)

    return tambahJam(selisih1, selisih2, selisih3)
}

fun selisihJam(waktu1: String, waktu2: String): String {
    val time1 = parseJam(waktu1)
    val time2 = parseJam(waktu2)

    return if (time1 != null && time2 != null) {
        val durasi = Duration.between(time1, time2)
        val jam = durasi.toHours()
        val menit = durasi.toMinutes() % 60

        String.format(Locale.US, "%02d:%02d", jam, menit)
    } else {
        "Format Salah (HH:MM)"
    }
}

fun tambahJam(jam1: String, jam2: String, jam3: String): String {
    val format = DateTimeFormatter.ofPattern("HH:mm")

    return try {
        val jam1konv = parseJam(jam1) ?: return "Format Salah (HH:MM)"
        val jam2konv = parseJam(jam2) ?: return "Format Salah (HH:MM)"
        val jam3konv = parseJam(jam3) ?: return "Format Salah (HH:MM)"

        var totalJam = jam1konv.hour + jam2konv.hour + jam3konv.hour
        var totalMenit = jam1konv.minute + jam2konv.minute + jam3konv.minute

        totalJam += totalMenit / 60
        totalMenit %= 60

        if (totalJam >= 24) {
            return "Jam tidak boleh melebihi 23:59"
        }

        String.format(Locale.US, "%02d:%02d", totalJam, totalMenit)
    } catch (e: Exception) {
        Log.d("SALAH", e.toString())
        "Format Salah (HH:MM)"
    }
}



@Composable
fun MasukResponse(context: Context, viewmodel: PegawaiListViewModel, navController: NavHostController){
    when(val addRequestResponse = viewmodel.addMasukResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            val hasilimport = ImportData(viewmodel, addRequestResponse.idsheet!!)
            Log.e("firestore", addRequestResponse.toString())

            if (hasilimport == "Import sukses!"){
                viewmodel.addMasukResponseReset()
                viewmodel.changeLoading(false)

                navController.navigate(Screen.Home.route)
            } else {
                Log.e("firestore", hasilimport)
            }
        }
        is Failure -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", addRequestResponse.e.toString())
            viewmodel.addMasukResponseReset()
        }
    }

}


@Composable
fun GetMasukResponse(context: Context, viewmodel: PegawaiListViewModel, action: (MasukRequest) -> Unit){
    when(val response = viewmodel.getMasukResponse){
        is Loading -> {
        }
        is Success -> {
            action(response.data!!)
            viewmodel.changeLoading(false)
            viewmodel.getMasukReset()

        }
        is Failure -> {
            viewmodel.changeLoading(false)
        }
    }

}

