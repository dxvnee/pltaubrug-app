package org.d3if3121.pltaconnect.ui.component

import android.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.theme.Warna.MerahNormal
import org.d3if3121.pltaconnect.ui.theme.Warna.MerahTua

@Composable
fun DataDua(
    ratacond: Boolean = false,
    tigacond: Boolean = false,
    duaratacond: Boolean = false,
    judul1cond: Boolean = true,

    judul1: String = "",
    judul2: String = "",
    ratarata:  String = "",
    warnarata1: androidx.compose.ui.graphics.Color = MerahNormal,

    text1k1: String,
    text2k1: String,
    text1k2: String,
    text2k2: String,
    text1k3: String = "",
    text2k3: String = "",

    judul2k2: String = "",
    ratarata2: String = "",
    warnarata2: androidx.compose.ui.graphics.Color = MerahNormal,

    hasil1: String = "",
    onHasil1Change: (String) -> Unit = {},
    hasil2: String = "",
    onHasil2Change: (String) -> Unit = {},
    hasil3: String = "",
    onHasil3Change: (String) -> Unit = {},

    besartext2: Int = 15
){
    Column (
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        if (judul1cond){
            Text(
                text = judul1,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Warna.MerahNormal,
            )

        }

        if (ratacond){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = judul2,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Warna.MerahNormal,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = ratarata,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = warnarata1,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
                if (duaratacond){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = judul2k2,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Warna.MerahNormal,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        Text(
                            text = ratarata2,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = warnarata2,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
            }

        }



        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputPutihKeterangan(
                text1 = text1k1,
                text2 = text2k1,
                inputan = hasil1,
                onInputanChange = onHasil1Change,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                besarhuruf = besartext2
            )
            InputPutihKeterangan(
                text1 = text1k2,
                text2 = text2k2,
                inputan = hasil2,
                onInputanChange = onHasil2Change,
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                besarhuruf = besartext2
            )
            if (tigacond){
                InputPutihKeterangan(
                    text1 = text1k3,
                    text2 = text2k3,
                    inputan = hasil3,
                    onInputanChange = onHasil3Change,
                    modifier = Modifier.weight(1f).padding(start = 16.dp),
                    besarhuruf = besartext2
                )
            }
        }
    }



}
