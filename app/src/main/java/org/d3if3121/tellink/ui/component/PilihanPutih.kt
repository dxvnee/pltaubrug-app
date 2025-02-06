package org.d3if3121.tellink.ui.component

import android.service.notification.Condition
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.tellink.ui.theme.Warna


@Composable
fun PilihanPutih(
    text1: String = "Text1",
    text2: String = "Text1",
    condition: Boolean = false,

    color1: ButtonColors = ButtonColors(
        containerColor =  Warna.MerahNormal,
        contentColor = Warna.PutihNormal,
        disabledContentColor = Warna.MerahNormal,
        disabledContainerColor = Warna.PutihNormal
    ),
    color2: ButtonColors = ButtonColors(
        containerColor =  Warna.PutihNormal,
        contentColor = Warna.MerahNormal,
        disabledContentColor = Warna.PutihNormal,
        disabledContainerColor = Warna.MerahNormal
    ),

    onclick1: () -> Unit = {},
    onclick2: () -> Unit = {}
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 17.dp)
            .height(50.dp),

        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Row (
            modifier = Modifier.padding(8.dp)
        ){
            Button(
                modifier = Modifier.size(50.dp).fillMaxWidth().weight(1f),
                shape = RoundedCornerShape(7.dp),
                colors = color1,
                onClick = {
                    onclick1()
                },
            ) {
                Text(
                    text = text1,
                    color = if(condition) Warna.MerahNormal else Warna.PutihNormal,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Button(
                modifier = Modifier.size(50.dp).fillMaxWidth().weight(1f),
                shape = RoundedCornerShape(7.dp),
                colors = color2,
                onClick = {
                    onclick2()
                },
            ) {
                Text(
                    text = text2,
                    color =if(condition) Warna.PutihNormal else  Warna.MerahNormal,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

        }
    }
}
