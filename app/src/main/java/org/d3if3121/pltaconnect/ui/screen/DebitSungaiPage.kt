package org.d3if3121.pltaconnect.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.ui.theme.Warna
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.pltaconnect.R
import org.d3if3121.pltaconnect.components.LoadingIndicator
import org.d3if3121.pltaconnect.core.printError
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.Project
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.ui.component.BottomBar
import org.d3if3121.pltaconnect.ui.component.TopBar
import org.d3if3121.pltaconnect.ui.component.cekScroll
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import org.d3if3121.pltaconnect.ui.viewmodel.ProjectListViewModel
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.Calendar
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.InputPutih
import org.d3if3121.pltaconnect.ui.component.InputPutihKeterangan
import org.d3if3121.pltaconnect.ui.component.JudulUtama


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DebitSungaiPage(
    navController: NavHostController,
    viewModel: PegawaiListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    var user = viewModel.user
    var context = LocalContext.current

    LaunchedEffect(user.nim) {
        projectviewmodel.getProjectListUser(user.nim)
    }

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            when(val projectListUserResponse = projectviewmodel.projectListUserResponse){
                is Loading -> LoadingIndicator()
                is Success -> projectListUserResponse.data.let { projectList ->
                        Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                            MainContentDebitSungai(
                                navController = navController,
                                lazyListState = lazyListState,
                                paddingValues = paddingValues,
                                viewmodel = viewModel,
                                projectviewmodel = projectviewmodel,
                                projectList = projectList!!,
                                user = user
                            )
                        }

                }
                is Failure -> printError(projectListUserResponse.e)
            }

        },
        bottomBar = {
            BottomBar(navController = navController, home = true){
//                viewModel.markProject(user.nim)
            }
        },
        contentColor = Warna.PutihNormal
    )
    DebitSungaiResponse(context, projectviewmodel)
}

@Composable
fun MainContentDebitSungai(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: PegawaiListViewModel,
    projectviewmodel: ProjectListViewModel,
    projectList: List<Project>,
    viewModel: PegawaiListViewModel = hiltViewModel(),
    user: Pegawai
) {

    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
            )
    )

    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp, top = padding)
    ) {

        JudulUtama(
            judul1 = "Debit Sungai",
            judul2 = "(Jam 24)",
            tanggal = "11 Februari 2025",
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Warna.PutihNormal),
            state = lazyListState
        ){
            item {

                Column {
                    Text(
                        text = "Pilih Unit: ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Warna.MerahNormal,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                    ButtonTiga(
                        onClick1 = {},
                        onClick2 = {},
                        onClick3 = {}
                    )

                    Row (
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Unit 1",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Normal,
                            color = Warna.MerahNormal,
                            modifier = Modifier.padding(top = 14.dp)
                        )
                    }
                }


                ProjectListProduksi()
            }

        }
    }
}

@Composable
fun ProjectListDebit(
){
    var maxdebit by remember { mutableStateOf("") }
    var mindebit by remember { mutableStateOf("") }
    var ratarata by remember { mutableStateOf("32,04") }

    var dam by remember { mutableStateOf("") }
    var kth by remember { mutableStateOf("") }
    var ph by remember { mutableStateOf("") }

    var maxdam by remember { mutableStateOf("") }
    var mindam by remember { mutableStateOf("") }

    var tma by remember { mutableStateOf("") }



    Card(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight(),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){

        Column (
            modifier = Modifier.padding(17.dp).fillMaxWidth()
        ){
            DataDua(
                ratacond = true,
                judul1 = "Debit Sungai Cicatih",
                judul2 = "Rata-rata:",
                warnarata1 = Warna.MerahNormal,
                ratarata = ratarata,
                text1k1 = "Maksimal:",
                text2k1 = "m2/d",
                text1k2 = "Minimal:",
                text2k2 = "m2/d",
                hasil1 = maxdebit,
                onHasil1Change = {
                    maxdebit = it
                },
                hasil2 = mindebit,
                onHasil2Change = {
                    mindebit = it
                } ,
            )

            DataDua(
                tigacond = true,
                judul1 = "Curah Hujan",
                text1k1 = "DAM:",
                text2k1 = "mm",
                text1k2 = "KTH:",
                text2k2 = "mm",
                text1k3 = "PH:",
                text2k3 = "mm",
                hasil1 = dam,
                onHasil1Change = {
                    dam = it
                },
                hasil2 = kth,
                onHasil2Change = {
                    kth = it
                } ,
                hasil3 = ph,
                onHasil3Change = {
                    ph = it
                } ,
            )

            DataDua(
                judul1 = "Diatas DAM",
                text1k1 = "Max:",
                text2k1 = "cm",
                text1k2 = "Min:",
                text2k2 = "cm",

                hasil1 = maxdam,
                onHasil1Change = {
                    maxdam = it
                },
                hasil2 = mindam,
                onHasil2Change = {
                    mindam = it
                } ,
            )

            InputPutihKeterangan(
                text1 = "TMA Rata-rata KTH :",
                inputan = tma,
                onInputanChange = {
                    tma = it
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )



        }

    }
    ButtonMerah(
        onClick = {

        },
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp).height(46.dp),
        content = {
            Text(
                text = "KIRIM",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                color = Warna.PutihNormal
            )
        }
    )


    Spacer(modifier = Modifier.height(150.dp))
}


@Composable
fun ProjectListProduksi(
){
    var sesudah by remember { mutableStateOf("") }
    var sebelum by remember { mutableStateOf("") }
    var selisih by remember { mutableStateOf("32,04") }
    var kwh by remember { mutableStateOf("90.118,54") }

    var rataair by remember { mutableStateOf("7776") }
    var air by remember { mutableStateOf("") }
    var ekonomisair by remember { mutableStateOf("") }


    var maxdam by remember { mutableStateOf("") }
    var mindam by remember { mutableStateOf("") }

    var tma by remember { mutableStateOf("") }



    Card(
        modifier = Modifier
            .fillMaxWidth().height(580.dp),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){

        Column (
            modifier = Modifier.padding(17.dp).fillMaxWidth()
        ){
            DataDua(
                ratacond = true,
                duaratacond = true,
                judul1 = "Produksi:",
                judul2 = "Selisih:",
                warnarata2 = Warna.BiruNormal,
                ratarata = selisih,
                text1k1 = "Sesudah:",
                text2k1 = "11 Januari 2025",
                text1k2 = "Sebelum:",
                text2k2 = "10 Januari 2025",

                judul2k2 = "kWh:",
                ratarata2 = kwh,
                hasil1 = sesudah,
                onHasil1Change = {
                    sesudah = it
                },
                hasil2 = sebelum,
                onHasil2Change = {
                    sebelum = it
                },
                besartext2 = 12
            )

            DataDua(
                ratacond = true,
                judul1 = "Data Air:",
                judul2 = "Rata-rata penggunaan air:",
                ratarata = rataair,
                warnarata1 = Warna.MerahTua,
                text1k1 = "Penggunaan Air:",
                text2k1 = "",
                text1k2 = "Ekonomis Air:",
                text2k2 = "",

                hasil1 = air,
                onHasil1Change = {
                    air = it
                },
                hasil2 = ekonomisair,
                onHasil2Change = {
                    ekonomisair = it
                },
            )

            DataDua(
                judul1 = "Lainnya:",
                text1k1 = "Pemakaian Sendiri:",
                text2k1 = "",
                text1k2 = "Penjualan:",
                text2k2 = "",

                hasil1 = maxdam,
                onHasil1Change = {
                    maxdam = it
                },
                hasil2 = mindam,
                onHasil2Change = {
                    mindam = it
                } ,
            )



        }

    }
    ButtonMerah(
        onClick = {

        },
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp).height(46.dp),
        content = {
            Text(
                text = "KIRIM",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                color = Warna.PutihNormal
            )
        }
    )


    Spacer(modifier = Modifier.height(150.dp))
}



@Composable
fun DebitSungaiResponse(context: Context, projectviewmodel: ProjectListViewModel){

    when(val addRequestResponse = projectviewmodel.addRequestResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Request Success!", Toast.LENGTH_SHORT).show()
            projectviewmodel.resetAddRequestResponse()
        }
        is Failure -> printError(addRequestResponse.e)
    }
    when(val deleteRequestResponse = projectviewmodel.deleteRequestResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Request Cancelled.", Toast.LENGTH_SHORT).show()
            projectviewmodel.resetDeleteRequestResponse()
        }
        is Failure -> printError(deleteRequestResponse.e)
    }
}
