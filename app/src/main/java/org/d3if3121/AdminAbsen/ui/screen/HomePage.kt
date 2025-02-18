package org.d3if3121.AdminAbsen.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
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
import org.d3if3121.AdminAbsen.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.AdminAbsen.data.model.Mahasiswa
import org.d3if3121.AdminAbsen.data.model.Project
import org.d3if3121.AdminAbsen.navigation.Screen
import org.d3if3121.AdminAbsen.ui.component.BottomBar
import org.d3if3121.AdminAbsen.ui.component.TopBar
import org.d3if3121.AdminAbsen.ui.component.cekScroll
import org.d3if3121.AdminAbsen.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.AdminAbsen.ui.viewmodel.ProjectListViewModel
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

            TopBar(
                lazyListState = lazyListState,
                helloActive = true,
                navController = navController,
                user = user
            )
        },
        content = { paddingValues ->
            projectviewmodel.getProjectListUser(user.nip)
            Column(modifier = Modifier.background(color = Warna.PutihNormal)) {
                MainContentHome(
                    navController = navController,
                    lazyListState = lazyListState,
                    paddingValues = paddingValues,
                    viewmodel = viewModel,
                    projectviewmodel = projectviewmodel,
                    projectList = emptyList(),
                    user = user
                )
            }

        },
        bottomBar = {
            BottomBar(navController = navController, home = true) {
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
    val numbers = remember { List(size = 200) { it } }
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
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            state = lazyListState
        ) {
            item {
                Text(
                    text = "Absensi",
                    color = Warna.Biru,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 4.dp)

                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

            }


            item {
                Column {
                    Spacer(modifier = Modifier.height(4.dp))

                    DatePickerUI(selectedDate = { selectedDate ->
                        Log.d("SelectedDate", selectedDate) // Bisa juga disimpan ke state
                    })


                    KeteranganAbsen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 80.dp), // Mengatur posisi tombol agar tidak menutupi BottomBar
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screen.Profile.route)
                                Toast.makeText(context, "Absen Berhasil!", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Warna.Biru),
                            modifier = Modifier
                                .fillMaxWidth() // Mengatur lebar tombol agar tidak terlalu besar
                                .height(60.dp).padding(top = 10.dp)
                        ) {
                            Text(text = "Edit", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Warna.PutihNormal)
                        }
                    }
                }
            }
        }
    }
}
// Kalender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUI(selectedDate: (String) -> Unit) { // Hapus PegawaiListViewModel
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    var selectedDateText by remember { mutableStateOf("") }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val newDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(dateFormatter)
            selectedDateText = newDate  // Update selectedDate
            selectedDate(newDate)       // Kirim ke parameter selectedDate
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Pusatkan secara horizontal
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(400.dp)
            .width(300.dp),
                contentAlignment = Alignment.Center // Pastikan berada di tengah
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier
                    .offset(y = -16.dp)
                    .scale(0.7f)
                    .padding(1.dp),
                colors = DatePickerDefaults.colors(
                    containerColor = Warna.PutihNormal,
                    titleContentColor = Warna.PutihNormal,
                    headlineContentColor = Warna.MerahTua,
                    weekdayContentColor = Color.Gray,
                    subheadContentColor = Color.LightGray,
                    yearContentColor = Warna.Merah,
                    todayContentColor = Color.Red,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Warna.MerahTua
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menampilkan tanggal yang dipilih
        Text(
            text = selectedDateText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Warna.MerahTua
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
                            modifier = Modifier.size(50.dp),
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
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "Absensi Masuk",
                    color = Warna.Biru,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "07.20",  // **Ganti dengan waktu masuk yang sesuai**
                    color = Warna.Biru,
                    fontSize = 19.sp,
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
                        modifier = Modifier.size(50.dp),
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
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "Absensi Pulang",
                    color = Warna.Biru,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "16.30",  // **Ganti dengan waktu pulang yang sesuai**
                    color = Warna.Biru,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

