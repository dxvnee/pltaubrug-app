package org.d3if3121.tellink.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.tellink.ui.theme.Warna


@Composable
fun KartuProfil(
    judul: String,
    konten: String,
){

    Text(
        text = judul,
        color = Warna.MerahNormal,
        fontSize = 21.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(bottom = 8.dp)

    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 20.dp),

        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(17.dp).fillMaxSize()
        ){
            Text(
                text = konten,
                color = Warna.HitamNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify
            )
        }





    }
}


@Composable
fun KartuProfilGambar(
    fotoprofil: Int,
    nama: String,
    jurusan: String,
    hari: String,

    judul: String,
    gambar: Int,
    konten: String,
){

    Text(
        text = judul,
        color = Warna.MerahNormal,
        fontSize = 21.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(bottom = 8.dp)

    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 20.dp),

        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(17.dp).fillMaxSize()
        ){

            Image(
                painter = painterResource(gambar),
                contentDescription = "Chat logo",
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))

            )

            Text(
                text = konten,
                color = Warna.HitamNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(top = 12.dp)
            )
        }





    }
}
