package org.d3if3121.pltaconnect.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.ui.theme.Warna


@Composable
fun BarisTigaText(
    text1k1: String,
    text2k1: String,
    textcolork1: androidx.compose.ui.graphics.Color,

    text1k2: String,
    text2k2: String,
    textcolork2: androidx.compose.ui.graphics.Color,

    text1k3: String,
    text2k3: String,
    textcolork3: androidx.compose.ui.graphics.Color,
){
    Row {
        TextTotal(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            text1 = text1k1,
            text2 = text2k1,
            text2color = textcolork1
        )
        TextTotal(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            text1 = text1k2,
            text2 = text2k2,
            text2color = textcolork2
        )
        TextTotal(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
            text1 = text1k3,
            text2 = text2k3,
            text2color = textcolork3
        )
    }
}

@Composable
fun TextTotal(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal,
    text1: String,
    text2: String,
    text2color: androidx.compose.ui.graphics.Color = Warna.BiruNormal

){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ){
        Text(
            text = text1,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = Warna.MerahNormal,
        )
        Text(
            text = text2,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = text2color,
            modifier = Modifier.padding(bottom = 4.dp)

        )
    }
}
