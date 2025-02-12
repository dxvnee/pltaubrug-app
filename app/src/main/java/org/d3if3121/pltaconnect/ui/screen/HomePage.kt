package org.d3if3121.pltaconnect.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.pltaconnect.ui.theme.Warna
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
import org.d3if3121.pltaconnect.R
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.Calendar
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


val TOP_BAR_HEIGHT = 70.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: PegawaiListViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()
    var user = viewModel.user
    var context = LocalContext.current



    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = true, navController = navController, user = user)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                MainContentHome(
                    navController = navController,
                    lazyListState = lazyListState,
                    paddingValues = paddingValues,
                    viewmodel = viewModel,
                )
            }

        },
        bottomBar = {
            BottomBar(navController = navController, home = true, pegawaiListViewModel = viewModel)
        },
        contentColor = Warna.PutihNormal
    )
    ResponseHome(context)
}

@Composable
fun ResponseHome(context: Context){

//    when(val addRequestResponse = projectviewmodel.addRequestResponse){
//        is Loading -> {
//
//        }
//        is Success -> {
//            Toast.makeText(context, "Request Success!", Toast.LENGTH_SHORT).show()
//            projectviewmodel.resetAddRequestResponse()
//        }
//        is Failure -> printError(addRequestResponse.e)
//    }
//    when(val deleteRequestResponse = projectviewmodel.deleteRequestResponse){
//        is Loading -> {
//
//        }
//        is Success -> {
//            Toast.makeText(context, "Request Cancelled.", Toast.LENGTH_SHORT).show()
//            projectviewmodel.resetDeleteRequestResponse()
//        }
//        is Failure -> printError(deleteRequestResponse.e)
//    }
}

@Composable
fun MainContentHome(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: PegawaiListViewModel = hiltViewModel(),
) {

    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
            )
    )

    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp)
    ) {

        LazyColumn(
            modifier = Modifier.padding(top = padding).fillMaxWidth().fillMaxHeight()
                .background(color = Warna.PutihNormal),
            state = lazyListState
        ){
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 17.dp).fillMaxWidth()
                ){
                    Text(
                        text = "Lihat Data",
                        color = Warna.MerahNormal,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold,

                    )
                }
                ProjectListHome(pegawaiListViewModel = viewmodel, navController = navController)


            }



        }
    }

}

@Composable
fun ProjectListHome(
    pegawaiListViewModel: PegawaiListViewModel,
    navController: NavHostController
){
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
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 20.dp)
                ){
                    Text(
                        text = "Spreadsheet: ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(300)
                    )
                    ClickableText(
                        text = AnnotatedString("Klik disini!"),
                        onClick = {
                        },
                        style = TextStyle.Default.copy(
                            Warna.MerahNormal,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500)
                        )
                    )
                }

                ButtonMerah(
                    onClick = {
                        pegawaiListViewModel.changeTanggal(selecteddate)
                        navController.navigate("ProjectPage/$selecteddate")
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                    content = {
                        Text(
                            text = "LIHAT",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = Warna.PutihNormal
                        )
                    }
                )














        }





    }


    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun EmptyView(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().height(550.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You're up to date!",
                color = Warna.MerahNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
            )
        }

    }
}


