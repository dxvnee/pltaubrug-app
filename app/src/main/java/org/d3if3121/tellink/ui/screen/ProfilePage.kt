package org.d3if3121.tellink.ui.screen

import android.annotation.SuppressLint
import org.d3if3121.tellink.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.theme.TellinkTheme
import org.d3if3121.tellink.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierInfo
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.d3if3121.tellink.navigation.BottomBarScreen
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardElevation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.tellink.ui.component.BottomBar
import org.d3if3121.tellink.ui.component.ButtonMerah
import org.d3if3121.tellink.ui.component.EditMahasiswaDialog
import org.d3if3121.tellink.ui.component.KartuProfil
import org.d3if3121.tellink.ui.component.KartuProfilGambar
import org.d3if3121.tellink.ui.component.PilihanPutih
import org.d3if3121.tellink.ui.component.TopBar
import org.d3if3121.tellink.ui.component.cekScroll
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel


@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfilePage(navController = rememberNavController())
}


@Composable
fun MainContentProfile(
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: MahasiswaListViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(false) }


    EditMahasiswaDialog(showDialog, viewmodel)

    val currentuser = viewmodel.user
    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
        )
    )
    var search by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.padding(top = padding).fillMaxWidth().fillMaxHeight(),
        state = lazyListState
    ){
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.header_2),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 20.dp)
                            .height(120.dp),

                        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
                        elevation = CardDefaults.cardElevation(20.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 15.dp,
                            bottomEnd = 15.dp
                        )
                    ){

                        Row (
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Column (
                                modifier = Modifier.width(117.dp).fillMaxHeight()
                            ){

                            }
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(start = 17.dp, top = 12.dp, bottom = 17.dp, end = 17.dp)
                            ){
                                Text(
                                    text = currentuser.nama,
                                    color = Warna.MerahNormal,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,

                                    )
                                Text(
                                    text = currentuser.nim,
                                    color = Warna.HitamNormal,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                Text(
                                    text = currentuser.jurusan,
                                    color = Warna.AbuTua,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Normal,
                                )

                                Row (
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.Bottom

                                ){
//                                    Text(
//                                        text = currentuser.totalpost.toString(),
//                                        color = Warna.HitamNormal,
//                                        fontSize = 15.sp,
//                                        fontWeight = FontWeight.Bold,
//                                    )
//
//                                    Text(
//                                        text = " Post",
//                                        color = Warna.HitamNormal,
//                                        fontSize = 15.sp,
//                                        fontWeight = FontWeight.Normal,
//                                    )
                                }



                            }



                        }
                    }
                }

                Column (
                    modifier = Modifier.fillMaxHeight().padding(top = 93.dp, start = 17.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(100.dp)
                    )
                    ButtonMerah(
                        onClick = {
                            showDialog.value = true
                        },
                        modifier = Modifier
                            .height(36.dp).width(100.dp).padding(top = 5.dp),
                        content = {
                            Text(
                                text = stringResource(id = R.string.edit),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = Warna.PutihNormal
                            )
                        }
                    )
                }

            }

            Column(
                modifier = Modifier.padding(start = 17.dp, end = 17.dp).background(color = Warna.PutihNormal)
            ) {
//                PilihanPutih()



            }
        }

        items(1){
            Column(
                modifier = Modifier.padding(start = 17.dp, end = 17.dp).background(color = Warna.PutihNormal)
            ){
//                KartuProfilGambar(
//                    fotoprofil = R.drawable.photo,
//                    nama = "Eigiya Daramuli Kale",
//                    jurusan = "D3 Rekayasa Perangkat Lunak Aplikasi",
//                    hari = "5 Days Ago",
//
//                    judul = "Achievement",
//                    gambar = R.drawable.post1,
//                    konten = "Ini Kenapa kodingan Java aku error " +
//                            "ya guys, tolong bantuannya dong" +
//                            "ya guys, tolong bantuannya dong" +
//                            "ya guys, tolong bantuannya dong" +
//                            "ya guys, tolong bantuannya dong"
//                )
//
//                KartuProfil(
//                    judul = "About Me",
//                    konten = "Ini Kenapa kodingan Java aku error " +
//                            "ya guys, tolong bantuannya dong" +
//                            "ya guys, tolong bantuannya dong" +
//                            "ya guys, tolong bantuannya dong" +
//                            "ya guys, tolong bantuannya dong"
//                )



            }

        }


    }


}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfilePage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel = hiltViewModel()
) {
    var search by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    val user = viewmodel.user

    Scaffold(
        topBar = {
//            TopBar(lazyListState = lazyListState, helloActive = false, profileActive = true,
//                search = search,
//                onSearchChange = {
//                    search = it
//                }
//            )
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.background(color = Warna.PutihNormal)) {
                MainContentProfile(lazyListState = lazyListState, paddingValues = paddingValues, viewmodel = viewmodel)
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )


}

