package org.d3if3121.pltaconnect.ui.screen.content

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.theme.Warna


@Composable
fun ContentProduksi(
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
){

    JudulUtama(
        judul1 = "Debit Sungai",
        judul2 = "(Jam 24)",
        tanggal = "11 Februari 2025",
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
                    onClick1 = {},
                    onClick2 = {},
                    onClick3 = {}
                )

                Row (
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Unit 1",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Normal,
                        color = Warna.MerahNormal,
                        modifier = Modifier.padding(top = 14.dp)
                    )
                }
            }
            MainContentProduksi()
        }

    }
}

@Composable
fun MainContentProduksi(
){
    var sesudah by remember { mutableStateOf("") }
    var sebelum by remember { mutableStateOf("") }
    var selisih by remember { mutableStateOf("32,04") }
    var kwh by remember { mutableStateOf("90.118,54") }

    var rataair by remember { mutableStateOf("7776") }
    var air by remember { mutableStateOf("") }
    var ekonomisair by remember { mutableStateOf("") }


    var maxdam by remember { mutableStateOf("") }
    var mindam by remember { mutableStateOf("") }

    var tma by remember { mutableStateOf("") }



    Card(
        modifier = Modifier
            .fillMaxWidth().height(580.dp),
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
                ratarata = selisih,
                text1k1 = "Sesudah:",
                text2k1 = "11 Januari 2025",
                text1k2 = "Sebelum:",
                text2k2 = "10 Januari 2025",

                judul2k2 = "kWh:",
                ratarata2 = kwh,
                hasil1 = sesudah,
                onHasil1Change = {
                    sesudah = it
                },
                hasil2 = sebelum,
                onHasil2Change = {
                    sebelum = it
                },
                besartext2 = 12
            )

            DataDua(
                ratacond = true,
                judul1 = "Data Air:",
                judul2 = "Rata-rata penggunaan air:",
                ratarata = rataair,
                warnarata1 = Warna.MerahTua,
                text1k1 = "Penggunaan Air:",
                text2k1 = "",
                text1k2 = "Ekonomis Air:",
                text2k2 = "",

                hasil1 = air,
                onHasil1Change = {
                    air = it
                },
                hasil2 = ekonomisair,
                onHasil2Change = {
                    ekonomisair = it
                },
            )

            DataDua(
                judul1 = "Lainnya:",
                text1k1 = "Pemakaian Sendiri:",
                text2k1 = "",
                text1k2 = "Penjualan:",
                text2k2 = "",

                hasil1 = maxdam,
                onHasil1Change = {
                    maxdam = it
                },
                hasil2 = mindam,
                onHasil2Change = {
                    mindam = it
                } ,
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

