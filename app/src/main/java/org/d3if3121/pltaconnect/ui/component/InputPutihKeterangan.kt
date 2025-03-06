package org.d3if3121.pltaconnect.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.R
import org.d3if3121.pltaconnect.ui.theme.Warna

@Composable
fun InputPutihKeterangan(
    text1: String,
    text2: String = "",
    inputan: String,
    onInputanChange: (String) -> Unit,
    modifier: Modifier = Modifier,

    besarhuruf: Int = 15
){
    Column (
        modifier = modifier.height(73.dp)
    ){
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                Text(
                    text = text1,
                    fontSize = besarhuruf.sp,
                    fontWeight = FontWeight.Bold,
                    color = Warna.MerahNormal,
                )
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = text2,
                        fontSize = besarhuruf.sp,
                        fontWeight = FontWeight.Normal,
                        color = Warna.MerahNormal,

                    )
                }

            }



        }
        InputPutih(
            input = inputan,
            placeholder = stringResource(id = R.string.enter_name),
            onInputChange = onInputanChange,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

    }
}