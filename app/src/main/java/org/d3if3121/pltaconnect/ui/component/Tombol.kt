package org.d3if3121.pltaconnect.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.ui.theme.Warna

@Composable
fun TombolGambar(
    painter: Painter,
    size: Int,
    onClick: () -> Unit,
){
    IconButton(onClick = onClick) {
        Image(
            painter = painter,
            contentDescription = "Chat logo",
            modifier = Modifier.size(size.dp)
        )
    }
}



@Composable
fun ButtonMerah(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor =  Warna.MerahNormal,
        contentColor =  Warna.PutihNormal
    ),
    corner: Dp = 18.dp
){
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(corner),
        content = {
            content()
        },
        colors = colors
    )
}

@Composable
fun ButtonTiga(
    onClick1: () -> Unit,
    onClick2: () -> Unit,
    onClick3: () -> Unit,

    text1: String = "Unit 1",
    text2: String = "Unit 2",
    text3: String = "Unit 3",

    text3size: Int = 15
){
    Row {
        ButtonMerah(
            onClick = onClick1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, end = 10.dp)
                .size(33.dp).weight(1f),
            content = {
                Text(
                    text = text1,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = Warna.PutihNormal
                )
            },
            corner = 9.dp,
            colors =  ButtonDefaults.buttonColors(
                containerColor =  Warna.BiruNormal,
                contentColor =  Warna.PutihNormal
            )
        )
        ButtonMerah(
            onClick = onClick2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .size(33.dp).weight(1f),
            content = {
                Text(
                    text = text2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = Warna.PutihNormal
                )
            },
            corner = 9.dp,
            colors =  ButtonDefaults.buttonColors(
                containerColor =  Warna.KuningMuda,
                contentColor =  Warna.PutihNormal
            )
        )
        ButtonMerah(
            onClick = onClick3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp)
                .size(33.dp).weight(1f),
            content = {
                Text(
                    text = text3,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = text3size.sp,
                    color = Warna.PutihNormal
                )
            },
            corner = 9.dp,
            colors =  ButtonDefaults.buttonColors(
                containerColor =  Warna.HijauMuda,
                contentColor =  Warna.PutihNormal
            )
        )

    }
}


@Composable
fun ButtonCommon(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    warna: Color = Warna.MerahNormal,
    onClick: () -> Unit,
){
    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = warna),
        shape = RoundedCornerShape(7.dp),
        modifier = modifier
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
