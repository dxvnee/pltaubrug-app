package org.d3if3121.absenubrugadmin.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonKecil(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    warna: Color,
    fontsize: Int = 16
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = warna),
        modifier = modifier
            .padding(end = 8.dp),
        shape = RoundedCornerShape(7.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = fontsize.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ButtonTiga(
    onClick1: () -> Unit,
    onClick2: () -> Unit,
    onClick3: () -> Unit,

    warna1: Color,
    warna2: Color,
    warna3: Color,

    text1: String,
    text2: String,
    text3: String,

    modifier: Modifier,
    absenpulang: Boolean = false
){

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        ButtonKecil (
            onClick = onClick1,
            modifier = modifier,
            text = text1,
            warna = warna1,
            fontsize = 9
        )
        ButtonKecil (
            onClick = onClick2,
            modifier = modifier,
            text =text2,
            warna = warna2,
            fontsize = 10

        )
        if(!absenpulang){
            ButtonKecil (
                onClick = onClick3,
                modifier = modifier,
                text = text3,
                warna = warna3,
                fontsize = 11
            )
        }

    }
}