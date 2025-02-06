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
fun KartuKonten(
    fotoprofil: Int,
    nama: String,
    jurusan: String,
    hari: String,

    judul: String,
    gambar: String,
    konten: String,
    request: Boolean = false,
    tag: List<String>  = emptyList(),
    type: String = "",
    requests: List<String> = emptyList(),

    onrequestchangetrue: () -> Unit = {},
    onrequestchangefalse: () -> Unit = {},
    onclick: () -> Unit = {},
    onclicktext: () -> Unit = {},
    onclickcancel: () -> Unit = {},
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(17.dp).fillMaxWidth().fillMaxHeight()
        ){
            Row (modifier = Modifier.padding(bottom = 17.dp).fillMaxWidth()){
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
                        text = jurusan,
                        color = Warna.AbuTua,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.offset(y = -3.dp)
                    )
                    Text(
                        text = hari,
                        color = Warna.AbuTua,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.offset(y = -8.dp)
                    )

                }

            }
            Text(
                text = judul,
                color = Warna.HitamNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 9.dp, bottom = 5.dp)
            )

            DisplayTag(tag)

            if (gambar != ""){
                Log.d("GAMBARRR", gambar)
                AsyncImage(
                    model = gambar,
                    contentDescription = "Project Image",
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Text(
                text = konten,
                color = Warna.HitamNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                textAlign = TextAlign.Justify
            )
            if (type == "project"){
                Column (
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        ButtonMerah(
                            onClick = {
                                onclick()
                            },
                            modifier = Modifier.weight(1f).size(46.dp),
                            content = {
                                Text(
                                    text = stringResource(id = R.string.edit),
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Warna.PutihNormal
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =  Warna.MerahNormal,
                                contentColor =  Warna.PutihNormal
                            )

                        )
                        Column (
                            modifier = Modifier.weight(3f).size(46.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ){
                            Text(
                                text = stringResource(id = R.string.requested),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Warna.AbuTua,
                                modifier = Modifier.clickable {
                                    onclicktext()
                                }
                                )
                        }

                        
                    }

                }

            } else if(type == "accept"){
                Log.d("tes3434", "ererer")
                Column (
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    ButtonMerah(
                        onClick = {
                            onclick()
                        },
                        modifier = Modifier.fillMaxWidth().size(46.dp),
                        content = {
                            Text(
                                text = stringResource(id = R.string.accepted),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = Warna.PutihNormal
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Warna.MerahNormal,
                            contentColor = Warna.PutihNormal
                        )

                    )
                }
            } else {
                Column (
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    ButtonMerah(
                        onClick = {
                            if (request) {
                                onclickcancel()
                                onrequestchangefalse()

                            } else {
                                onclick()
                                onrequestchangetrue()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().size(46.dp),
                        content = {
                            Text(
                                text = if(request) stringResource(id = R.string.cancel) else stringResource(id = R.string.request),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = if(request) Warna.MerahNormal else Warna.PutihNormal
                            )
                        },
                        colors = if (request) {
                            ButtonDefaults.buttonColors(
                                containerColor =  Warna.AbuMuda,
                                contentColor =  Warna.MerahNormal
                            )
                        } else {
                            ButtonDefaults.buttonColors(
                                containerColor =  Warna.MerahNormal,
                                contentColor =  Warna.PutihNormal
                            )
                        },

                        )
                }

            }




        }





    }
}
