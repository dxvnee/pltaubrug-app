package org.d3if3121.tellink.ui.component

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.d3if3121.tellink.R
import org.d3if3121.tellink.ui.theme.Warna


@Composable
fun KartuProfilPutih(
    fotoprofil: Int,
    nama: String,
    nim: String,
    jurusan: String,
    accepted: Boolean = false,


    onclick: () -> Unit = {},
    onclickkick: () -> Unit = {},
    onclickchat: () -> Unit = {},
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight().padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(17.dp).fillMaxWidth().fillMaxHeight()
        ){
            Row (
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            ){
                Column (
                    modifier = Modifier.padding(end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                ){
                    Image(
                        painter = painterResource(id = fotoprofil),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(52.dp)
                    )
                }

                Column {
                    Text(
                        text = nama,
                        color = Warna.MerahNormal,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = nim,
                        color = Warna.AbuTua,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.offset(y = -3.dp)
                    )
                    Text(
                        text = jurusan,
                        color = Warna.AbuTua,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.offset(y = -8.dp)
                    )

                }
                if(accepted == false){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.fillMaxHeight().fillMaxWidth()
                    ) {
                        ButtonMerah(
                            onClick = {
                                onclick()
                            },
                            modifier = Modifier.size(50.dp),
                            content = {
                                Text(
                                    text = stringResource(id = R.string.acc),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    color = Warna.PutihNormal,
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =  Warna.MerahNormal,
                                contentColor =  Warna.PutihNormal
                            )

                        )

                    }
                } else {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        ) {
                            ButtonMerah(
                                onClick = {
                                    onclickkick()
                                },
                                modifier = Modifier.size(50.dp),
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.kick),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp,
                                        color = Warna.PutihNormal,
                                        textAlign = TextAlign.Center
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =  Warna.MerahNormal,
                                    contentColor =  Warna.PutihNormal
                                )

                            )

                        }
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        ) {
                            ButtonMerah(
                                onClick = {
                                    onclickchat()
                                },
                                modifier = Modifier.size(50.dp),
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.chat),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp,
                                        color = Warna.PutihNormal,
                                        textAlign = TextAlign.Center
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =  Warna.MerahNormal,
                                    contentColor =  Warna.PutihNormal
                                )

                            )

                        }
                    }

                }





            }



        }





    }
}
