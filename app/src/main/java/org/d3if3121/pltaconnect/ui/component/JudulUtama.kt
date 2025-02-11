package org.d3if3121.pltaconnect.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.pltaconnect.ui.theme.Warna

@Composable
fun JudulUtama(
    judul1: String,
    judul1size: Int = 21,
    judul2: String = "",
    onJudul2Change: (String) -> Unit = {},
    judul2cond: Boolean = true,
    tanggal: String,

    onClickBack: () -> Unit,
    onClickNext: () -> Unit,

){
    var expanded by remember { mutableStateOf(false) }
    var paddingbutton = 12

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 17.dp).fillMaxWidth()
    ){
        Column(
            modifier = if (!judul2cond) Modifier.padding(top = paddingbutton.dp).weight(1f) else Modifier.weight(1f),
        ) {
            ButtonKecil(
                imageVector = Icons.Default.ArrowBackIosNew,
                onClick = onClickBack
            )
        }

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = if (!judul2cond) Modifier.padding(top = paddingbutton.dp).weight(2f) else Modifier.weight(2f)
        ){
            Text(
                text = judul1,
                color = Warna.MerahNormal,
                fontSize = judul1size.sp,
                fontWeight = FontWeight.ExtraBold,

                )
            if(judul2cond){
                Box(
                    modifier = Modifier.clickable {
                        expanded = true
                    }
                ){
                    Text(
                        text = judul2,
                        color = Warna.MerahNormal,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ){
                        DropdownMenuItem(
                            content = {
                                Text(
                                    text = "Jam 10",
                                    color = Warna.MerahNormal,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic
                                )
                            },
                            onClick = {
                                onJudul2Change("(Jam 10)")
                                expanded = false
                            },
                        )
                        DropdownMenuItem(
                            content = {
                                Text(
                                    text = "Jam 24",
                                    color = Warna.MerahNormal,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic
                                )
                            },
                            onClick = {
                                onJudul2Change("(Jam 24)")
                                expanded = false
                            },
                        )
                    }
                }

            }

            Text(
                text = tanggal,
                color = Warna.MerahNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic
            )


        }

        Column(
            modifier = if (!judul2cond) Modifier.padding(top = paddingbutton.dp).weight(1f) else Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            ButtonKecil(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = onClickNext,
            )
        }
    }
}