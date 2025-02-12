package org.d3if3121.pltaconnect.ui.screen.content

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.component.PindahUnit
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel


@Composable
fun ContentPemakaian(
    tanggal: String,
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    viewmodel: PegawaiListViewModel = hiltViewModel()

){
    var kva by remember { mutableStateOf("50 kVA") }
    var judul2 by remember { mutableStateOf("(Jam 24)") }

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
            MainContentPemakaian()
        }

    }
}

@Composable
fun MainContentPemakaian(
){
    var sesudah by remember { mutableStateOf("") }
    var sebelum by remember { mutableStateOf("") }
    var kwh by remember { mutableStateOf("32,04") }
    var kwhkumulatif by remember { mutableStateOf("90.118,54") }

    var rataair by remember { mutableStateOf("7776") }
    var air by remember { mutableStateOf("") }
    var ekonomisair by remember { mutableStateOf("") }


    var maxdam by remember { mutableStateOf("") }
    var mindam by remember { mutableStateOf("") }

    var tma by remember { mutableStateOf("") }



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
                onHasil1Change = {
                    sesudah = it
                },
                hasil2 = sebelum,
                onHasil2Change = {
                    sebelum = it
                },
                besartext2 = 12
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
            text = "90.204,45",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Warna.BiruNormal,
            modifier = Modifier.padding(bottom = 4.dp)

        )

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

