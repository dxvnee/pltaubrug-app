package org.d3if3121.tellink.ui.screen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.tellink.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.tellink.components.LoadingIndicator
import org.d3if3121.tellink.core.printError
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response.Success
import org.d3if3121.tellink.data.model.Response.Loading
import org.d3if3121.tellink.data.model.Response.Failure
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.component.BottomBar
import org.d3if3121.tellink.ui.component.KartuKonten
import org.d3if3121.tellink.ui.component.TopBar
import org.d3if3121.tellink.ui.component.cekScroll
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.ui.viewmodel.ProjectListViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter



@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(navController = rememberNavController())
}


val TOP_BAR_HEIGHT = 70.dp



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    var user = viewModel.user
    var context = LocalContext.current

    Scaffold(
        topBar = {
            Log.d("usersekaranghome", user.toString())

            TopBar(lazyListState = lazyListState, helloActive = true, navController = navController, user = user)
        },
        content = { paddingValues ->
            projectviewmodel.getProjectListUser(user.nim)
            when(val projectListUserResponse = projectviewmodel.projectListUserResponse){
                is Loading -> LoadingIndicator()
                is Success -> projectListUserResponse.data.let { projectList ->

                        Log.d("HASILNYA", projectList.toString())
                        Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                            MainContentHome(
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
}


@Composable
fun MainContentHome(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: MahasiswaListViewModel,
    projectviewmodel: ProjectListViewModel,
    projectList: List<Project>,
    viewModel: MahasiswaListViewModel = hiltViewModel(),
    user: Mahasiswa
) {


    var context = LocalContext.current
    var request = remember { mutableStateOf(false) }
    val numbers = remember { List(size = 200){ it } }
    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
            )
    )
    var search by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp)
    ) {

        LazyColumn(
            modifier = Modifier.padding(top = 6.dp).fillMaxWidth().fillMaxHeight()
                .background(color = Warna.PutihNormal),
            state = lazyListState
        ){
            item {
                Text(
                    text = "Absensi",
                    color = Warna.MerahNormal,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 4.dp)

                    )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 17.dp)

                ){
                }
            }


                item {
                    Column {
                        Spacer(modifier = Modifier.height(4.dp))

                        DatePickerUI()

                            KeteranganAbsen(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 80.dp), // Mengatur posisi tombol agar tidak menutupi BottomBar
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate(Screen.Project.route)

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                                modifier = Modifier
                                    .fillMaxWidth() // Mengatur lebar tombol agar tidak terlalu besar
                                    .height(80.dp).padding(top = 10.dp)
                            ) {
                                Text(text = "Absen", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(50.dp))

                }
            }



        }
    }
// Kalender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUI() {
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("Belum dipilih") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp).padding(top = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mengatur posisi/ukuran kalender
        DatePicker(
            state = datePickerState,
            modifier = Modifier.fillMaxWidth()
                .scale(0.7f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Menampilkan tanggal yang dipilih secara otomatis
        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { millis ->
                val localDate = Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                selectedDate = localDate.format(dateFormatter)
            }
        }

        // Teks "Tanggal" yang diperbarui otomatis saat user memilih di kalender
        Text(
            text = "Tanggal: $selectedDate",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF0797DA),
        )
    }
}

@Composable
fun KeteranganAbsen(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // **Bagian kiri (Absensi Masuk)**
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Warna.Hijau)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Check",
                            modifier = Modifier.size(45.dp),
                            tint = Warna.PutihNormal
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "HADIR",
                    color = Warna.Hijau,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "Absensi Masuk",
                    color = Warna.MerahNormal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "07.20",  // **Ganti dengan waktu masuk yang sesuai**
                    color = Warna.MerahNormal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // **Bagian kanan (Absensi Pulang)**
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End // Mengatur elemen ke kanan
        ) {
            // Ikon check (sebelah kiri)
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Warna.Hijau)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Check",
                        modifier = Modifier.size(45.dp),
                        tint = Warna.PutihNormal
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp)) // Beri jarak antara ikon dan teks

            // Kolom teks (di sebelah kanan ikon)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start // Pastikan teks rata kiri
            ) {
                Text(
                    text = "HADIR",
                    color = Warna.Hijau,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "Absensi Pulang",
                    color = Warna.MerahNormal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "16.30",  // **Ganti dengan waktu pulang yang sesuai**
                    color = Warna.MerahNormal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

