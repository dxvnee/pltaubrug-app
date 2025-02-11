package org.d3if3121.pltaconnect.ui.screen.content

import android.graphics.Color
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.R
import org.d3if3121.pltaconnect.ui.component.BarisTigaText
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.InputPutih
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.component.PindahUnit
import org.d3if3121.pltaconnect.ui.theme.Warna


@Composable
fun ContentMasuk(
    tanggal: String,
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
){
    var unit by remember { mutableStateOf("Unit 1") }

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
            MainContentMasuk()
        }

    }
}


@Composable
fun MainContentMasuk(
){
    var masuk by remember { mutableStateOf("") }
    var keluar by remember { mutableStateOf("") }
    var jamkerja by remember { mutableStateOf("24:00:00") }
    var kwh by remember { mutableStateOf("90.118,54") }
    var keterangan by remember { mutableStateOf("") }

    var rataair by remember { mutableStateOf("7776") }
    var air by remember { mutableStateOf("") }
    var ekonomisair by remember { mutableStateOf("") }


    var maxdam by remember { mutableStateOf("") }
    var mindam by remember { mutableStateOf("") }

    var tma by remember { mutableStateOf("") }



    Card(
        modifier = Modifier
            .fillMaxWidth().height(240.dp),
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
                ratarata = jamkerja,
                text1k1 = "Masuk:",
                text2k1 = "11 Januari 2025",
                text1k2 = "Keluar:",
                text2k2 = "10 Januari 2025",

                hasil1 = masuk,
                onHasil1Change = {
                    masuk = it
                },
                hasil2 = keluar,
                onHasil2Change = {
                    keluar = it
                },
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
                    onInputChange = { input ->
                        keterangan = input
                    },
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.fillMaxWidth().height(200.dp)

                )
            }

        }

        ButtonMerah(
            onClick = {

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

