package org.d3if3121.absenubrugadmin.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
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
import org.d3if3121.absenubrugadmin.ui.component.DialogEditJam
import org.d3if3121.absenubrugadmin.ui.component.DialogEditProfile
import org.d3if3121.absenubrugadmin.ui.component.DialogLoading
import org.d3if3121.absenubrugadmin.ui.component.KeteranganAbsen
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: MahasiswaListViewModel = hiltViewModel(),
) {
    val userid = viewModel.userId.collectAsState().value

    DialogLoading(viewModel)

    LaunchedEffect (Unit, userid){
        userid?.let {
            viewModel.getMahasiswa(it)
        }
    }

    val lazyListState = rememberLazyListState()
    val user  = viewModel.user
    val context = LocalContext.current

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
                        .padding(top = paddingValues.calculateTopPadding() - 50.dp)
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
    ResponseHome(viewModel, context)
}

@Composable
fun ResponseHome(viewmodel: MahasiswaListViewModel, context: Context) {
    when (val response = viewmodel.mahasiswaListResponse) {
        is Response.Success -> {
            viewmodel.changeLoading(false)
        }

        is Response.Failure -> {
            Toast.makeText(context, response.e.toString(), Toast.LENGTH_SHORT).show()
        }

        is Response.Loading -> {
        }
    }
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
        viewmodel.getJam()
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

    var gantijam by remember { mutableStateOf(false) }
    var context = LocalContext.current

    if(gantijam){
        DialogEditJam(viewmodel) {
            gantijam = false
        }
    }
    HomeResponse(viewmodel, context){
        gantijam = false
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
            KeteranganAbsen(
                hadir1 = "Jam Masuk",
                hadir2 = "Jam Keluar",
                jam1 = viewmodel.jam.masuk,
                jam2 = viewmodel.jam.keluar,
                modifier = Modifier.clickable {
                    gantijam = true
                }.padding(bottom = 9.dp)
            )


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
            Text(
                text = "Input ke Spreadsheet!",
                color = Warna.MerahNormal,
                fontSize = 12.sp,
                modifier = Modifier.clickable {

                }
            )
        }
    }

    Spacer(modifier = Modifier.height(50.dp))

}


@Composable
fun HomeResponse(
    viewmodel: MahasiswaListViewModel, context: Context, onShowDialogChange: () -> Unit
){
    when(val response = viewmodel.absenListResponse){
        is Response.Loading -> {}
        is Response.Success -> response.data?.let {

            viewmodel.changeLoading(false)
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

    when(val response = viewmodel.editJamResponse){
        is Response.Success -> {
            viewmodel.changeLoading(false)
            Toast.makeText(context, response.data, Toast.LENGTH_SHORT).show()
            onShowDialogChange()
            viewmodel.editJamResponseReset()
        }

        is Response.Failure -> {
            viewmodel.changeLoading(false)
            Toast.makeText(context, response.e.toString(), Toast.LENGTH_SHORT).show()
            viewmodel.editJamResponseReset()

        }
        is Response.Loading -> {
            viewmodel.changeLoading(false)

        }
    }
}






