package org.d3if3121.absenubrugadmin.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Input
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.Output
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrugadmin.ui.theme.Warna


@Composable
fun KeteranganAbsen(
    modifier: Modifier = Modifier,
    hadir1: String,
    hadir2: String,
    jam1: String,
    jam2: String,
    telat: String = "",
    telat2: String = ""
) {

    Row(
        modifier = modifier
            .fillMaxWidth().fillMaxHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        KeteranganAbsenComponent(hadir1, telat, jam1, modifier.weight(1f))
        KeteranganAbsenComponent(hadir2, telat2, jam2, modifier.weight(1f))
    }
}


@Composable
fun KeteranganAbsenComponent(
    hadir1 : String,
    telat : String,
    jam1: String,
    modifier: Modifier = Modifier
){

    var telatCon by remember { mutableStateOf(telat == "TELAT") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .padding(end = 3.dp)

        ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(KondisiWarna(hadir1))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = KondisiIkon(hadir1),
                    contentDescription = "Check",
                    modifier = Modifier.size(25.dp),
                    tint = Warna.PutihNormal
                )
            }
        }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
        ) {


            Text(
                text = if(hadir1 == "Belum Absen") "Belum" else hadir1,
                color = KondisiWarna(hadir1),
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = jam1,
                    color = if (telatCon) Color.Red else Warna.MerahNormal,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                )

            }
        }

    }

}


fun KondisiWarna(keterangan: String): Color {
    var warna: Color = Warna.BiruText
    when(keterangan){
        "Hadir" -> {
            warna = Warna.Hijau
        }
        "Izin" -> {
            warna = Warna.Kuning
        }
        "Cuti" -> {
            warna = Warna.Merah
        }
        "Belum Absen" -> {
            warna = Color.Gray
        }
        "Jam Masuk" -> {
            warna = Color.Gray
        }
        "Jam Keluar" -> {
            warna = Color.Gray
        }
    }
    return warna
}


fun KondisiIkon(keterangan: String): ImageVector {
    var icon: ImageVector = Icons.Filled.Check
    when(keterangan){
        "Hadir" -> {
            icon = Icons.Filled.Check
        }
        "Izin" -> {
            icon = Icons.Filled.LinearScale
        }
        "Cuti" -> {
            icon = Icons.Filled.DoNotDisturb
        }
        "Belum Absen" -> {
            icon = Icons.Filled.Clear
        }
        "Jam Masuk" -> {
            icon = Icons.AutoMirrored.Filled.Input
        }
        "Jam Keluar" -> {
            icon = Icons.Filled.Output
        }

    }
    return icon
}