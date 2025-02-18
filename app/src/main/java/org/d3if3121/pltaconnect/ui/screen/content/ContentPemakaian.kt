package org.d3if3121.pltaconnect.ui.screen.content

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.model.data.Pemakaian
import org.d3if3121.pltaconnect.data.model.data.PemakaianRequest
import org.d3if3121.pltaconnect.data.repository.ImportData
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.component.PindahUnit
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import java.util.Locale
import kotlin.math.abs


@Composable
fun ContentPemakaian(
    tanggal: String,
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    viewmodel: PegawaiListViewModel = hiltViewModel(),
    navController: NavHostController

){
    var kva by remember { mutableStateOf("50 kVA") }
    var judul2 by remember { mutableStateOf("(Jam 24)") }
    var context = LocalContext.current

    var kva1 by remember { mutableStateOf(Pemakaian()) }
    var kva2 by remember { mutableStateOf(Pemakaian()) }
    var kva3 by remember { mutableStateOf(Pemakaian()) }
    var kvatotal by remember { mutableStateOf(Pemakaian()) }

    var sebelumsheet by remember { mutableStateOf("231617")}

    var checkbox1 by remember { mutableStateOf(true) }
    var checkbox2 by remember { mutableStateOf(true) }
    var checkbox3 by remember { mutableStateOf(true) }

    LaunchedEffect(kva1, kva2, kva3, checkbox1, checkbox2, checkbox3){
        kva1 = hitungPemakaianSendiri(kva = kva1, sebelumsheet = sebelumsheet, checkbox = checkbox1)
        kva2 = hitungPemakaianSendiri(kva = kva2, sebelumsheet = sebelumsheet, checkbox = checkbox2)
        kva3 = hitungPemakaianSendiri(kva = kva3, sebelumsheet = sebelumsheet, checkbox = checkbox3)

        kvatotal = totalPemakaian(kva1, kva2, kva3)
    }

    PemakaianResponse(
        context = context,
        viewmodel = viewmodel,
        navController = navController
    )

    JudulUtama(
        judul1 = "Pemakaian Sendiri",
        judul1size = 20,
        judul2 = judul2,
        tanggal = tanggal,
        onClickBack =  onClickBack,
        onClickNext =  onClickNext,

        onJudul2Change = {
            judul2 = it
        }
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .background(color = Warna.PutihNormal),
        state = lazyListState
    ){
        item {

            Column {
                Text(
                    text = "Pilih kVA: ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Warna.MerahNormal,
                    modifier = Modifier.padding(top = 10.dp)
                )

                ButtonTiga(
                    onClick1 = {
                        kva = "50 kVA"
                    },
                    onClick2 = {
                        kva = "160 kVA"
                    },
                    onClick3 = {
                        kva = "2 x 250 kVA"
                    },

                    text1 = "50 kVA",
                    text2 = "160 kVA",
                    text3 = "2 x 250 kVA",

                    text3size = 11
                )

                Row (
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    PindahUnit(
                        unit =  kva,
                        cond1 = "50 kVA",
                        cond2 = "160 kVA",
                        cond3 = "2 x 250 kVA",
                    )
                }
            }

            when(kva){
                "50 kVA" -> {
                    MainContentPemakaian1(
                        kwh = kva1.kwh,
                        kwhkumulatif = kva1.kwhkumulatif,

                        sesudah = kva1.sesudah,
                        onSesudahChange = {
                            kva1 = kva1.copy(sesudah = it)
                        },
                        sebelum = kva1.sebelum,
                        onSebelumChange = {
                            kva1 = kva1.copy(sebelum = it)
                        },
                        checkbox = checkbox1,
                        onCheckboxChange = {
                            checkbox1 = it
                        },
                        viewmodel = viewmodel

                    )
                }
                "160 kVA" -> {
                    MainContentPemakaian2(
                        kwh = kva2.kwh,
                        kwhkumulatif = kva2.kwhkumulatif,

                        sesudah = kva2.sesudah,
                        onSesudahChange = {
                            kva2 = kva2.copy(sesudah = it)
                        },
                        sebelum = kva2.sebelum,
                        onSebelumChange = {
                            kva2 = kva2.copy(sebelum = it)
                        },
                        checkbox = checkbox2,
                        onCheckboxChange = {
                            checkbox2 = it
                        },
                        viewmodel = viewmodel

                    )
                }

                "2 x 250 kVA" -> {
                    MainContentPemakaian3(
                        kwh = kva3.kwh,
                        kwhkumulatif = kva3.kwhkumulatif,

                        sesudah = kva3.sesudah,
                        onSesudahChange = {
                            kva3 = kva3.copy(sesudah = it)
                        },
                        sebelum = kva3.sebelum,
                        onSebelumChange = {
                            kva3 = kva3.copy(sebelum = it)
                        },
                        checkbox = checkbox3,
                        onCheckboxChange = {
                            checkbox3 = it
                        },
                        viewmodel = viewmodel

                    )

                }
            }
            Column (
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Normal,
                    fontSize = 21.sp,
                    color = Warna.MerahNormal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "kWh:",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = Warna.MerahNormal,
                    modifier = Modifier.padding(bottom = 4.dp)


                )
                Text(
                    text = kvatotal.kwh,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Warna.BiruNormal,
                    modifier = Modifier.padding(bottom = 4.dp)

                )

                ButtonMerah(
                    onClick = {
                        val id = tanggal + "_pemakaian_" + judul2
                        viewmodel.addPemakaian(
                            PemakaianRequest(
                                id = id,
                                kwh1 = kva1.kwh,
                                kwh2 = kva2.kwh,
                                kwh3 = kva3.kwh,
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
}

@Composable
fun MainContentPemakaian1(
    kwh: String,
    kwhkumulatif: String,

    onSesudahChange: (String) -> Unit,
    sesudah: String,

    sebelum: String,
    onSebelumChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckboxChange: (Boolean) -> Unit,
    viewmodel: PegawaiListViewModel

){
   MainContent(
       kwh = kwh,
       kwhkumulatif = kwhkumulatif,
       sesudah = sesudah,
       onSesudahChange = onSesudahChange,
       sebelum = sebelum,
       onSebelumChange = onSebelumChange,
       checkbox = checkbox,
       onCheckboxChange = onCheckboxChange,
       viewmodel = viewmodel
   )
}

@Composable
fun MainContentPemakaian2(
    kwh: String,
    kwhkumulatif: String,

    onSesudahChange: (String) -> Unit,
    sesudah: String,

    sebelum: String,
    onSebelumChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckboxChange: (Boolean) -> Unit,
    viewmodel: PegawaiListViewModel

){
    MainContent(
        kwh = kwh,
        kwhkumulatif = kwhkumulatif,
        sesudah = sesudah,
        onSesudahChange = onSesudahChange,
        sebelum = sebelum,
        onSebelumChange = onSebelumChange,
        checkbox = checkbox,
        onCheckboxChange = onCheckboxChange,
        viewmodel = viewmodel

    )
}

@Composable
fun MainContentPemakaian3(
    kwh: String,
    kwhkumulatif: String,

    onSesudahChange: (String) -> Unit,
    sesudah: String,

    sebelum: String,
    onSebelumChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckboxChange: (Boolean) -> Unit,
    viewmodel: PegawaiListViewModel

){
    MainContent(
        kwh = kwh,
        kwhkumulatif = kwhkumulatif,
        sesudah = sesudah,
        onSesudahChange = onSesudahChange,
        sebelum = sebelum,
        onSebelumChange = onSebelumChange,
        checkbox = checkbox,
        onCheckboxChange = onCheckboxChange,
        viewmodel = viewmodel

    )
}

@Composable
fun MainContent(
    kwh: String,
    kwhkumulatif: String,

    onSesudahChange: (String) -> Unit,
    sesudah: String,

    sebelum: String,
    onSebelumChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckboxChange: (Boolean) -> Unit,

    viewmodel: PegawaiListViewModel
){

    Card(
        modifier = Modifier
            .fillMaxWidth().height(230.dp),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){

        Column (
            modifier = Modifier.padding(17.dp).fillMaxWidth()
        ){
            DataDua(
                ratacond = true,
                duaratacond = true,
                judul1 = "Stand Meter:",
                judul2 = "kWh:",
                warnarata2 = Warna.BiruNormal,
                ratarata = kwh,
                text1k1 = "Sesudah:",
                text2k1 = "11 Januari 2025",
                text1k2 = "Sebelum:",
                text2k2 = "10 Januari 2025",

                judul2k2 = "kWh Kumulatif:",
                ratarata2 = kwhkumulatif,
                hasil1 = sesudah,
                onHasil1Change = onSesudahChange,
                hasil2 = sebelum,
                onHasil2Change = onSebelumChange,
                besartext2 = 12
            )
        }
    }

    CheckboxBiru(
        checked = checkbox,
        onCheckedChange = onCheckboxChange
    )

}


fun hitungPemakaianSendiri(kva : Pemakaian, sebelumsheet: String, checkbox: Boolean): Pemakaian {
    var sebelum = kva.sebelum.toFloatSafe()
    var sesudah = kva.sesudah.toFloatSafe()

    var kwh = abs(sebelum - sesudah)
    var kwhkumulatif = abs(sebelum + kwh)

    return if (!checkbox){
        kva.copy(
            sebelum = sebelumsheet,
            kwh = String.format(Locale.US, "%.2f", kwh),
            kwhkumulatif = String.format(Locale.US, "%.2f", kwhkumulatif)
        )
    } else {
        kva.copy(
            kwh = String.format(Locale.US, "%.2f", kwh),
            kwhkumulatif = String.format(Locale.US, "%.2f", kwhkumulatif)
        )
    }
}

fun totalPemakaian(
    kva1: Pemakaian,
    kva2: Pemakaian,
    kva3: Pemakaian,
): Pemakaian{
    return Pemakaian (
        kwh = listOf(kva1.kwh, kva2.kwh, kva3.kwh)
            .map { it.toFloatOrNull() ?: 0f }
            .sum()
            .toString()
    )
}


@Composable
fun PemakaianResponse(context: Context, viewmodel: PegawaiListViewModel, navController: NavHostController){
    when(val addRequestResponse = viewmodel.addPemakaianResponse){
        is Loading -> {
        }
        is Success -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            val hasilimport = ImportData(viewmodel, addRequestResponse.idsheet!!)
            Log.e("firestore", addRequestResponse.toString())

            if (hasilimport == "Import sukses!"){
                viewmodel.addPemakaianResponseReset()
                navController.navigate(Screen.Home.route)
            } else {
                Log.e("firestore", hasilimport)
            }
        }
        is Failure -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", addRequestResponse.e.toString())
            viewmodel.addPemakaianResponseReset()
        }
    }

}


