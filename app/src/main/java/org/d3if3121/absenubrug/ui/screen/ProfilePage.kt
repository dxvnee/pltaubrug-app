package org.d3if3121.absenubrug.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrug.ui.theme.Warna
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.absenubrug.ui.component.BottomBar
import org.d3if3121.absenubrug.ui.component.TopBar
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrug.R
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.navigation.Screen
import org.d3if3121.absenubrug.ui.component.DialogLoading
import org.d3if3121.absenubrug.ui.component.FotoProfil


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
    DialogLoading(viewmodel)


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        Log.d("WHY", imageUri.toString())
        imageUri?.let {
            viewmodel.addFotoProfil(viewmodel.user.nip, imageUri!!)
        }
    }

    FotoProfilResponse(viewmodel, context, navController)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(paddingValues).padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FotoProfil(
            imageUrl = viewmodel.user.foto,
            modifier = Modifier
                .padding(top = 50.dp)
                .size(145.dp).clickable {
                    launcher.launch("image/*")
                }
        )


        Spacer(modifier = Modifier.height(12.dp))

        // Nama & Email
        Text(
            text = viewmodel.user.nama,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = viewmodel.user.nip,
            fontSize = 16.sp,
            color = Warna.HitamNormal
        )

        viewmodel.user.role.forEach {
            Text(
                text = it,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }


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


@Composable
fun FotoProfilResponse(viewmodel: MahasiswaListViewModel, context: Context, navController: NavHostController){
    when(val response = viewmodel.addFotoProfil){
        is Response.Success -> {
            viewmodel.changeLoading(false)
            Toast.makeText(context, "Upload foto berhasil!", Toast.LENGTH_SHORT).show()
            viewmodel.addFotoProfilReset()

        }
        is Response.Failure -> {
            viewmodel.changeLoading(false)

            Toast.makeText(context, response.e.toString(), Toast.LENGTH_SHORT).show()
        }
        is Response.Loading -> {
            viewmodel.changeLoading(false)

        }
    }
}


