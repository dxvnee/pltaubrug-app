package org.d3if3121.AdminAbsen.ui.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.AdminAbsen.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.AdminAbsen.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import org.d3if3121.AdminAbsen.components.LoadingIndicator
import org.d3if3121.AdminAbsen.core.printError
import org.d3if3121.AdminAbsen.data.model.Mahasiswa
import org.d3if3121.AdminAbsen.data.model.Project
import org.d3if3121.AdminAbsen.data.model.Response.Failure
import org.d3if3121.AdminAbsen.data.model.Response.Loading
import org.d3if3121.AdminAbsen.data.model.Response.Success
import org.d3if3121.AdminAbsen.ui.component.BottomBar
import org.d3if3121.AdminAbsen.ui.component.InputPutih
import org.d3if3121.AdminAbsen.ui.component.PilihanPutih
import org.d3if3121.AdminAbsen.ui.component.TambahProjectDialog
import org.d3if3121.AdminAbsen.ui.component.TopBar
import org.d3if3121.AdminAbsen.ui.component.cekScroll
import org.d3if3121.AdminAbsen.ui.component.uploadImageToFirebase
import org.d3if3121.AdminAbsen.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.AdminAbsen.ui.viewmodel.ProjectListViewModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Preview(showBackground = true)
@Composable
fun ProjectPagePreview() {
    ProjectPage(navController = rememberNavController())
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectPage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()

    val user = viewmodel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->

            LazyColumn(  // Gunakan LazyColumn agar bisa di-scroll
                modifier = Modifier
                    .fillMaxSize()
                    .background(Warna.PutihNormal)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 80.dp) // Tambahkan padding agar tidak tertutup BottomBar
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, top = 16.dp, bottom = 8.dp),
                        contentAlignment = Alignment.Center
                    ){   // Tambahkan Judul Page "Absensi"
                    Text(
                        text = "Data Absensi",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Warna.Biru
                    )

                    }
                }

                // Menampilkan 3 card
                items(3) {
                    UserProfileCard(user)
                }
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}





@Composable
fun UserProfileCard(user: Mahasiswa) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto Profil di sebelah kiri
                Image(
                    painter = painterResource(R.drawable.photo),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp)) // Spasi antara foto dan teks

                // Informasi user
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Nama Pegawai",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Warna.Biru
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "NIP Pegawai",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tombol Edit di pojok kanan bawah
            Button(
                onClick = {
                    // Aksi edit profil bisa ditambahkan di sini
                },
                colors = ButtonDefaults.buttonColors(containerColor = Warna.Biru),
                modifier = Modifier.align(Alignment.End) // Menempelkan tombol ke pojok kanan
            ) {
                Text(text = "Edit", color = Color.White)
            }
        }
    }
}








