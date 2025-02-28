package org.d3if3121.absenubrugadmin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrugadmin.R
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel


@Composable
fun CardList(
    absen: Absen,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp).padding(top = 10.dp, bottom = 9.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
                .padding(17.dp)
        ) {
            Column(
                modifier = Modifier.width(200.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(45.dp)
                    )

                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = absen.nama,
                            color = Warna.MerahNormal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,

                            )
                        Text(
                            text = absen.nip,
                            color = Warna.HitamNormal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic

                        )
                    }
                }
            }
            KeteranganAbsen(
                hadir1 = absen.keterangan,
                hadir2 = absen.keterangan2,
                jam1 = absen.jam,
                jam2 = absen.jam2,
                telat = absen.telat,
                telat2 = absen.telat2
            )


        }
    }
}
@Composable
fun CardListPegawai(
    pegawai: Mahasiswa,
    onClick: () -> Unit
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp).padding(top = 10.dp, bottom = 9.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
                .padding(17.dp)
        ) {
            Column(
                modifier = Modifier.width(200.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(45.dp)
                    )

                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = pegawai.nama,
                            color = Warna.MerahNormal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,

                            )
                        Text(
                            text = pegawai.nip,
                            color = Warna.HitamNormal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic

                        )
                        Row{
                            pegawai.role.forEach {
                                Text(
                                    text = "$it ",
                                    color = Warna.HitamNormal,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }

                    }
                }
            }



        }
    }
}