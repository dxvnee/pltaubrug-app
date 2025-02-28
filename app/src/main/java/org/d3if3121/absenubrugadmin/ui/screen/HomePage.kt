package org.d3if3121.absenubrugadmin.ui.screen

import android.annotation.SuppressLint
import android.util.Log

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Response
import org.d3if3121.absenubrugadmin.navigation.Screen
import org.d3if3121.absenubrugadmin.ui.component.BottomBar
import org.d3if3121.absenubrugadmin.ui.component.Calendar
import org.d3if3121.absenubrugadmin.ui.component.DialogLoading
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: MahasiswaListViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()
    var user  = viewModel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = true, navController = navController, user = user)
        },
        content = { paddingValues ->

            Box(
                modifier = Modifier.background(color = Warna.PutihNormal).fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding() - 50.dp,

                        )
                ) {
                    MainContentHome(
                        navController = navController,
                        lazyListState = lazyListState,
                        paddingValues = paddingValues,
                        viewmodel = viewModel,
                    )
                }

            }
        },
        bottomBar = {
            BottomBar(navController = navController, home = true, mahasiswaListViewModel = viewModel)
        },
        contentColor = Warna.PutihNormal
    )
}

@Composable
fun MainContentHome(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
) {
    LaunchedEffect (Unit){
        viewmodel.getMahasiswaList()
    }

    DialogLoading(viewmodel)

    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxWidth().fillMaxHeight()
                .background(color = Warna.PutihNormal),
            state = lazyListState
        ){
            item {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding( bottom = 17.dp).fillMaxWidth()
                ){
                    Text(
                        text = "Lihat Data",
                        color = Warna.MerahNormal,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                ProjectListHome(pegawaiListViewModel = viewmodel, navController = navController, viewmodel = viewmodel)
            }
        }
    }

}

@Composable
fun ProjectListHome(
    pegawaiListViewModel: MahasiswaListViewModel,
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel
){

    HomeResponse(viewmodel)

    if(viewmodel.absenList!!.isEmpty()){
        LaunchedEffect(viewmodel.tanggal) {
            val absenList = viewmodel.absenList
            val absen = absenList?.firstOrNull { it.tanggal == viewmodel.tanggal }

            if (absen != null) {
                viewmodel.changeAbsen(absen)
                viewmodel.changeJamHome(absen.jam, absen.jam2)
                viewmodel.changeKeteranganHome(absen.keterangan, absen.keterangan2)
            } else {
                viewmodel.changeJamHome("-", "-")
                viewmodel.changeKeteranganHome("Belum Absen", "Belum Absen")
                viewmodel.changeAbsen(Absen(keterangan = "Belum Absen"))
            }
        }
    } else {


    }

    var selecteddate by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Calendar(
            pegawaiListViewModel = pegawaiListViewModel,
            selectedDate = {
                selecteddate = it
            }
        )

        Column (
            modifier = Modifier.padding(17.dp).fillMaxWidth().fillMaxHeight()
        ){
            Log.d("absenlist", viewmodel.absenList.toString())

            Button(
                onClick = {
                    pegawaiListViewModel.changeTanggal(selecteddate)
                    navController.navigate(Screen.Employee.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "LIHAT", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    Spacer(modifier = Modifier.height(50.dp))

}


@Composable
fun HomeResponse(
    viewmodel: MahasiswaListViewModel
){
    when(val response = viewmodel.absenListResponse){
        is Response.Loading -> {}
        is Response.Success -> response.data?.let {

            viewmodel.changeLoading(false)
            viewmodel.changeList(it)
            viewmodel.changeAbsenListResponse()
        }
        is Response.Failure -> {
            Log.d("error", response.e.toString())
        }
    }

    when(val response = viewmodel.mahasiswaListResponse){
        is Response.Loading -> {}
        is Response.Success -> response.data?.let {

        }
        is Response.Failure -> {
            Log.d("error", response.e.toString())
        }
    }
}



