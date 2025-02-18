package org.d3if3121.tellink.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.tellink.R
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.component.ButtonMerah
import org.d3if3121.tellink.ui.theme.Warna


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPage(
    navController: NavHostController,
    projectId: String? = "996GZuXUa03N1JRmSkyM",
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Warna.PutihNormal, RectangleShape)
    ) {

        Column {
            Row(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pln),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .width(263.dp)
                        .height(70.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Canvas(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 525.dp)
                .size(400.dp)

        ) {

            drawCircle(
                color = Warna.MerahTua,
                radius = size.minDimension
            )


        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 280.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .height(450.dp)
                .width(203.dp),

            colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
            elevation = CardDefaults.cardElevation(20.dp),
            shape = RoundedCornerShape(15.dp)
        )
        {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.depositphotos_138297620_stock_illustration_successful_businessman_with_best_thumbs),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .width(99.dp)
                        .height(234.dp),
                    contentScale = ContentScale.Fit

                )
                Text(
                    text = "Registrasi Anda telah diterima" +
                            " dan sedang menunggu" +
                            " persetujuan admin. " +
                            " Anda akan menerima notifikasi " + "setelah akun " +
                            "Anda disetujui. ",
                    fontSize = 14.sp,
                    color = Warna.BiruText,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(300),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )


            }


        }
    }
}
