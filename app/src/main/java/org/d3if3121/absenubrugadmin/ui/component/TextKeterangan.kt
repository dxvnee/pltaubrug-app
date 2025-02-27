package org.d3if3121.absenubrugadmin.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrugadmin.ui.theme.Warna

@Composable
fun TextKeterangan(
    telat: Boolean,
    jamtelat: String,
    selectedStatus: String,
    absenpulang: Boolean = false,
){
    if(telat){
        Text(
            text = if(!absenpulang) "Telat " + jamtelat else "Lebih Cepat " + jamtelat,
            color = Color.Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = if(!absenpulang) "Tidak Telat" else "Tepat Waktu!",
            color = Warna.Hijau,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    Text(
        text = "Keterangan: $selectedStatus",
        color = Warna.MerahNormal,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}