package org.d3if3121.absenubrugadmin.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.component.BottomBar
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrugadmin.R
import org.d3if3121.absenubrugadmin.navigation.Screen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfilePage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel,
) {
    var search by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    val user = viewmodel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Warna.PutihNormal)
            ) {
                ProfilePageContent(paddingValues, viewmodel, navController)
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}

@Composable
fun ProfilePageContent(
    paddingValues: PaddingValues,
    viewmodel: MahasiswaListViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(paddingValues).padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto Profil
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape).padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = "Foto Profil",
                modifier = Modifier.size(80.dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Nama & Email
        Text(
            text = viewmodel.user.nama,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = viewmodel.user.nim,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewmodel.loginResponseReset()
                navController.navigate(Screen.Login.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Edit", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Log out", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}



