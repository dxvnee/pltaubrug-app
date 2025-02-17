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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.model.data.Produksi
import org.d3if3121.pltaconnect.data.model.data.ProduksiRequest
import org.d3if3121.pltaconnect.data.repository.ImportData
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.component.BarisTigaText
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
fun ContentProduksi(
    tanggal: String,
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    viewmodel: PegawaiListViewModel = hiltViewModel(),
    navController: NavHostController

){
    var judul2 by remember { mutableStateOf("(Jam 10)") }
    var context = LocalContext.current

    var unit by remember { mutableStateOf("Unit 1") }

    var unit1 by remember { mutableStateOf(Produksi(sesudah = "16393.22", sebelum = "16371.57"))}
    var unit2 by remember { mutableStateOf(Produksi())}
    var unit3 by remember { mutableStateOf(Produksi())}
    var unittotal by remember { mutableStateOf(Produksi(pemakaian = "0")) }

    var sheetpemakaiansendiri by remember { mutableStateOf("254") }
    var isClicked by remember { mutableStateOf(false) }
    var checkbox1 by remember { mutableStateOf(false) }
    var checkbox2 by remember { mutableStateOf(false) }
    var checkbox3 by remember { mutableStateOf(false) }

    var errorinfo by remember { mutableStateOf("")}


    LaunchedEffect(unit1, unit2, unit3) {
        unit1 = hitungUnitProduksi(unit1, unittotal, sheetpemakaiansendiri, checkbox1)
        unit2 = hitungUnitProduksi(unit2, unittotal, sheetpemakaiansendiri, checkbox2)
        unit3 = hitungUnitProduksi(unit3, unittotal, sheetpemakaiansendiri, checkbox3)

        unittotal = RumusTambah(unit1, unit2, unit3, sheetpemakaiansendiri)
    }

    ProduksiResponse(
        context = context,
        viewmodel = viewmodel,
        navController = navController
    )

    JudulUtama(
        judul1 = "Produksi",
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
                    MainContentProduksiUnit1(
                        data = unit1,
                        onSesudahChange = {
                            unit1 = unit1.copy(sesudah = it)
                        },
                        onSebelumChange = {
                            unit1 = unit1.copy(sebelum = it)
                        },
                        onEkonomisChange = {
                            unit1 = unit1.copy(ekonomisair = it)
                        },
                        onAirChange = {
                            unit1 = unit1.copy(air = it)
                        },
                        onPemakaianChange = {
                            unit1 = unit1.copy(pemakaian = it)
                        },
                        onPenjualanChange = {
                            unit1 = unit1.copy(penjualan = it)
                        },
                        checkbox = checkbox1,
                        onCheckedChange = {
                            checkbox1 = it
                        }
                    )

                }
                "Unit 2" ->{
                    MainContentProduksiUnit2(
                        data = unit2,
                        onSesudahChange = {
                            unit2 = unit2.copy(sesudah = it)},
                        onSebelumChange = {
                            unit2 = unit2.copy(sebelum = it)},
                        onEkonomisChange = {
                            unit2 = unit2.copy(ekonomisair = it)},
                        onAirChange = {
                            unit2 = unit2.copy(air = it)},
                        onPemakaianChange = {
                            unit2 = unit2.copy(pemakaian = it)},
                        onPenjualanChange = {
                            unit2 = unit2.copy(penjualan = it)},
                        checkbox = checkbox2,
                        onCheckedChange = {
                            checkbox2 = it
                        }
                    )
                }
                "Unit 3" ->{
                    MainContentProduksiUnit3(
                        data = unit3,
                        onSesudahChange = {
                            unit3 = unit3.copy(sesudah = it)},
                        onSebelumChange = {
                            unit3 = unit3.copy(sebelum = it)},
                        onEkonomisChange = {
                            unit3 = unit3.copy(ekonomisair = it)},
                        onAirChange = {
                            unit3 = unit3.copy(air = it)},
                        onPemakaianChange = {
                            unit3 = unit3.copy(pemakaian = it)},
                        onPenjualanChange = {
                            unit3 = unit3.copy(penjualan = it)},
                        checkbox = checkbox3,
                        onCheckedChange = {
                            checkbox3 = it
                        }
                    )
                }
            }


            Column (
                modifier = Modifier.padding(top = 14.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Normal,
                    fontSize = 21.sp,
                    color = Warna.MerahNormal,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                BarisTigaText(
                    text1k1 = "Produksi:",
                    text2k1 = unittotal.kwh,
                    textcolork1 = Warna.BiruNormal,

                    text1k2 = "Pemakaian Sendiri:",
                    text2k2 = unittotal.pemakaian,
                    textcolork2 = Warna.MerahNormal,

                    text1k3 = "Pemakaian Air:",
                    text2k3 = unittotal.air,
                    textcolork3 = Warna.MerahTua,
                )

                BarisTigaText(
                    text1k1 = "Rata-rata Air:",
                    text2k1 = unittotal.rataair,
                    textcolork1 = Warna.MerahTua,

                    text1k2 = "Ekonomis Air:",
                    text2k2 = unittotal.ekonomisair,
                    textcolork2 = Warna.MerahTua,

                    text1k3 = "Penjualan Air:",
                    text2k3 = unittotal.penjualan,
                    textcolork3 = Warna.MerahNormal,
                )

            }

            Column (
                modifier = Modifier.padding(top = 14.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ButtonMerah(
                    onClick = {
                        isClicked = true
                        if(isClicked){

                            val id = tanggal + "_produksi_" + judul2
                            viewmodel.addProduksi(
                                ProduksiRequest(
                                    id = id,
                                    sesudah1 = unit1.sesudah,
                                    sebelum1 = unit1.sebelum,
                                    selisih1 = unit1.selisih,
                                    kwh1 = unit1.kwh,
                                    rataair1 = unit1.rataair,
                                    air1 = unit1.air,
                                    ekonomisair1 = unit1.ekonomisair,
                                    pemakaian1 = unit1.pemakaian,
                                    penjualan1 = unit1.penjualan,

                                    sesudah2 = unit2.sesudah,
                                    sebelum2 = unit2.sebelum,
                                    selisih2 = unit2.selisih,
                                    kwh2 = unit2.kwh,
                                    rataair2 = unit2.rataair,
                                    air2 = unit2.air,
                                    ekonomisair2 = unit2.ekonomisair,
                                    pemakaian2 = unit2.pemakaian,
                                    penjualan2 = unit2.penjualan,

                                    sesudah3 = unit3.sesudah,
                                    sebelum3 = unit3.sebelum,
                                    selisih3 = unit3.selisih,
                                    kwh3 = unit3.kwh,
                                    rataair3 = unit3.rataair,
                                    air3 = unit3.air,
                                    ekonomisair3 = unit3.ekonomisair,
                                    pemakaian3 = unit3.pemakaian,
                                    penjualan3 = unit3.penjualan,
                                )
                            )
                            isClicked = false
                        }

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
fun CheckboxBiru(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Warna.MerahTua
            )
        )

        Text(
            text = "Isi manual",
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = Warna.HitamNormal,
        )
    }
}


@Composable
fun MainContentProduksiUnit1(
    data: Produksi,
    onSesudahChange: (String) -> Unit,
    onSebelumChange: (String) -> Unit,

    onEkonomisChange: (String) -> Unit,
    onAirChange: (String) -> Unit,

    onPemakaianChange: (String) -> Unit,
    onPenjualanChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    MainContent(
        data = data,
        onSesudahChange = onSesudahChange,
        onSebelumChange = onSebelumChange,

        onEkonomisChange = onEkonomisChange,
        onAirChange = onAirChange,

        onPemakaianChange = onPemakaianChange,
        onPenjualanChange = onPenjualanChange,

        checkbox = checkbox,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun MainContentProduksiUnit2(
    data: Produksi,
    onSesudahChange: (String) -> Unit,
    onSebelumChange: (String) -> Unit,

    onEkonomisChange: (String) -> Unit,
    onAirChange: (String) -> Unit,

    onPemakaianChange: (String) -> Unit,
    onPenjualanChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    MainContent(
        data = data,
        onSesudahChange = onSesudahChange,
        onSebelumChange = onSebelumChange,

        onEkonomisChange = onEkonomisChange,
        onAirChange = onAirChange,

        onPemakaianChange = onPemakaianChange,
        onPenjualanChange = onPenjualanChange,

        checkbox = checkbox,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun MainContentProduksiUnit3(
    data: Produksi,
    onSesudahChange: (String) -> Unit,
    onSebelumChange: (String) -> Unit,

    onEkonomisChange: (String) -> Unit,
    onAirChange: (String) -> Unit,

    onPemakaianChange: (String) -> Unit,
    onPenjualanChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    MainContent(
        data = data,
        onSesudahChange = onSesudahChange,
        onSebelumChange = onSebelumChange,

        onEkonomisChange = onEkonomisChange,
        onAirChange = onAirChange,

        onPemakaianChange = onPemakaianChange,
        onPenjualanChange = onPenjualanChange,

        checkbox = checkbox,
        onCheckedChange = onCheckedChange
    )
}


@Composable
fun MainContent(
    data: Produksi,
    onSesudahChange: (String) -> Unit,
    onSebelumChange: (String) -> Unit,

    onEkonomisChange: (String) -> Unit,
    onAirChange: (String) -> Unit,

    onPemakaianChange: (String) -> Unit,
    onPenjualanChange: (String) -> Unit,

    checkbox: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth().height(560.dp),
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
                judul1 = "Produksi:",
                judul2 = "Selisih:",
                warnarata2 = Warna.BiruNormal,
                ratarata = data.selisih,
                text1k1 = "Sesudah:",
                text2k1 = "11 Januari 2025",
                text1k2 = "Sebelum:",
                text2k2 = "10 Januari 2025",

                judul2k2 = "kWh:",
                ratarata2 = data.kwh,
                hasil1 = data.sesudah,
                onHasil1Change = onSesudahChange,
                hasil2 = data.sebelum,
                onHasil2Change = onSebelumChange,
                besartext2 = 12
            )

            DataDua(
                ratacond = true,
                judul1 = "Data Air:",
                judul2 = "Rata-rata penggunaan air:",
                ratarata = data.rataair,
                warnarata1 = Warna.MerahTua,
                text1k1 = "Penggunaan Air:",
                text2k1 = "",
                text1k2 = "Ekonomis Air:",
                text2k2 = "",

                hasil1 = data.air,
                onHasil1Change = onAirChange,
                hasil2 = data.ekonomisair,
                onHasil2Change = onEkonomisChange
            )

            DataDua(
                judul1 = "Lainnya: (kWh)",
                text1k1 = "Pemakaian Sendiri:",
                text2k1 = "",
                text1k2 = "Penjualan:",
                text2k2 = "",

                hasil1 = data.pemakaian,
                onHasil1Change = onPemakaianChange,
                hasil2 = data.penjualan,
                onHasil2Change = onPenjualanChange,
            )
        }
    }
    CheckboxBiru(checked = checkbox, onCheckedChange = onCheckedChange)
}

fun RumusTambah(
    unit1: Produksi,
    unit2: Produksi,
    unit3: Produksi,
    sheet: String = "0.0"
): Produksi {
    return Produksi(
        kwh = listOf(unit1.kwh, unit2.kwh, unit3.kwh)
            .map { it.toFloatOrNull() ?: 0f }
            .sum()
            .toString(),
        ekonomisair = listOf(unit1.ekonomisair, unit2.ekonomisair, unit3.ekonomisair)
            .map { it.toFloatOrNull() ?: 0f }
            .sum()
            .toString(),
        rataair = listOf(unit1.rataair, unit2.rataair, unit3.rataair)
            .map { it.toFloatOrNull() ?: 0f }
            .sum()
            .toString(),
        pemakaian = sheet,
        air = listOf(unit1.air, unit2.air, unit3.air)
            .map { it.toFloatOrNull() ?: 0f }
            .sum()
            .toString(),
        penjualan = listOf(unit1.penjualan, unit2.penjualan, unit3.penjualan)
            .map { it.toFloatOrNull() ?: 0f }
            .sum()
            .toString()
    )
}

fun String.toFloatSafe(): Float = this.toFloatOrNull() ?: 0f

fun hitungUnitProduksi(
    unit: Produksi,
    unittotal: Produksi,
    sheetpemakaiansendiri: String,
    checkbox: Boolean
): Produksi {
    val sebelum = unit.sebelum.toFloatSafe()
    val sesudah = unit.sesudah.toFloatSafe()
    val air = unit.air.toFloatSafe()
    val kwh = unit.kwh.toFloatSafe()
    val totalKwh = unittotal.kwh.toFloatSafe()
    val totalPemakaian = unittotal.pemakaian.toFloatSafe()

    val selisihkonv = abs(sesudah - sebelum)
    val selisihkonvRounded = String.format(Locale.US, "%.2f", selisihkonv).toFloat()

    val kali = selisihkonvRounded * 8590.9
    val rumusrataair = abs((air / 3600.0) / 24.0)

    val rumusekonomisair = if (kwh == 0f) 0f else abs(air / kwh)
    val rumusair = abs(((kwh / 1000) * 3600) * 1.8)
    val rumuspemakaian = if (totalKwh == 0f) 0f else abs((kwh / totalKwh) * totalPemakaian)
    val rumuspenjualan = abs(kwh - sheetpemakaiansendiri.toFloatSafe())

    return if(checkbox) {
        unit.copy(
            selisih = String.format(Locale.US, "%.2f", selisihkonv),
            kwh = String.format(Locale.US, "%.2f", kali),
            rataair = String.format(Locale.US, "%.9f", rumusrataair),
        )
    } else {
        return unit.copy(
            selisih = String.format(Locale.US, "%.2f", selisihkonv),
            kwh = String.format(Locale.US, "%.2f", kali),
            rataair = String.format(Locale.US, "%.9f", rumusrataair),
            ekonomisair = String.format(Locale.US, "%.2f", rumusekonomisair),
            air = String.format(Locale.US, "%.0f", rumusair),
            pemakaian = String.format(Locale.US, "%.1f", rumuspemakaian),
            penjualan = String.format(Locale.US, "%.0f", rumuspenjualan)
        )
    }


}

@Composable
fun ProduksiResponse(context: Context, viewmodel: PegawaiListViewModel, navController: NavHostController){
    when(val addRequestResponse = viewmodel.addProduksiResponse){
        is Loading -> {
        }
        is Success -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            ImportData(viewmodel)
            Log.e("firestore", addRequestResponse.toString())
            viewmodel.addProduksiResponseReset()
            navController.navigate(Screen.Home.route)
        }
        is Failure -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", addRequestResponse.e.toString())
            viewmodel.addProduksiResponseReset()
        }
    }

}


