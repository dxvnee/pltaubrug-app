package org.d3if3121.absenubrug.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrug.ui.theme.Warna


@Composable
fun KeteranganAbsen(
    modifier: Modifier = Modifier,
    hadir1: String,
    hadir2: String,
    jam1: String,
    jam2: String,
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f).padding(end = 3.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
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
                        modifier = Modifier.size(35.dp),
                        tint = Warna.PutihNormal
                    )
                }
            }

            Spacer(modifier = Modifier.width(7.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = hadir1,
                    color = KondisiWarna(hadir1),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "Absensi Masuk",
                    color = Warna.MerahNormal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = jam1,
                    color = Warna.MerahNormal,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }

        Row(
            modifier = Modifier.weight(1f).padding(start = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(KondisiWarna(hadir2))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector =  KondisiIkon(hadir2),
                        contentDescription = "Check",
                        modifier = Modifier.size(35.dp),
                        tint = Warna.PutihNormal
                    )
                }
            }

            Spacer(modifier = Modifier.width(7.dp))

            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = hadir2,
                    color = KondisiWarna(hadir2),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "Absensi Pulang",
                    color = Warna.MerahNormal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = jam2,
                    color = Warna.MerahNormal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
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
        "Tidak Hadir" -> {
            warna = Warna.Merah
        }
        "Belum Absen" -> {
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
        "Tidak Hadir" -> {
            icon = Icons.Filled.DoNotDisturb
        }
        "Belum Absen" -> {
            icon = Icons.Filled.Clear
        }
    }
    return icon
}