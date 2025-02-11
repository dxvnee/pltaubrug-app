package org.d3if3121.pltaconnect.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.ui.theme.Warna

@Composable
fun PindahUnit(
    unit: String,
    cond1: String = "Unit 1",
    cond2: String = "Unit 2",
    cond3: String = "Unit 3",
){
    when(unit){
        cond1 -> {
            Text(
                text = cond1,
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                color = Warna.MerahNormal,
                modifier = Modifier.padding(top = 14.dp)
            )
        }
        cond2 -> {
            Text(
                text = cond2,
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                color = Warna.MerahNormal,
                modifier = Modifier.padding(top = 14.dp)
            )
        }
        cond3 -> {
            Text(
                text = cond3,
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                color = Warna.MerahNormal,
                modifier = Modifier.padding(top = 14.dp)
            )
        }
    }
}