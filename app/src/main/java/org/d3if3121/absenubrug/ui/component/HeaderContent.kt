package org.d3if3121.absenubrug.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
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
import org.d3if3121.absenubrug.ui.theme.Warna
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel


@Composable
fun HeaderContent(
    viewmodel: MahasiswaListViewModel,
    masukpergi: String,
    onclick1: () -> Unit,
    onclick2: () -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Absensi",
                color = Warna.MerahNormal,
                fontSize = 21.sp,
                fontWeight = FontWeight.ExtraBold,

                )
            Text(
                text = viewmodel.tanggal,
                color = Warna.MerahNormal,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic

            )

            Box(
                modifier = Modifier.clickable {
                    expanded = true
                }
            ){
                Text(
                    text = masukpergi,
                    color = Warna.MerahNormal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
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
                                text = "Masuk",
                                color = Warna.MerahNormal,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        onClick = {
                            onclick1()
                            expanded = false
                        },
                    )
                    DropdownMenuItem(
                        content = {
                            Text(
                                text = "Pulang",
                                color = Warna.MerahNormal,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic
                            )
                        },
                        onClick = {
                            onclick2()
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}
