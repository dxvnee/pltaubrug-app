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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import org.d3if3121.absenubrug.ui.component.ButtonIcon
import org.d3if3121.absenubrug.ui.component.DialogEditProfile
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
            Box (
                modifier = Modifier.fillMaxSize().background(Warna.PutihNormal)
            ){
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
    LaunchedEffect(Unit){
        viewmodel.getMahasiswa(viewmodel.user.nip)
    }

    DialogLoading(viewmodel)

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        Log.d("WHY", imageUri.toString())
        imageUri?.let {
            viewmodel.addFotoProfil(viewmodel.user.nip, imageUri!!)
        }
    }

    DialogEditProfile(viewmodel = viewmodel, showDialog = showDialog, onDismissRequest = { showDialog = false })

    FotoProfilResponse(viewmodel, context, navController)
    EditProfilResponse(viewmodel, context){ showDialog = false }

    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues).padding(top = 30.dp, start = 17.dp, end = 17.dp),
                colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth().padding(17.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    FotoProfil(
                        imageUrl = viewmodel.user.foto,
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .size(145.dp).clickable {
                                launcher.launch("image/*")
                            }
                    )


                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = viewmodel.user.nama,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Row{
                        Text(
                            text = viewmodel.user.nip + " - ",
                            fontSize = 16.sp,
                            color = Warna.HitamNormal
                        )
                        Text(
                            text = viewmodel.user.posisi,
                            fontSize = 16.sp,
                            color = Warna.HitamNormal
                        )
                    }


                    viewmodel.user.role.forEach {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }


                    Spacer(modifier = Modifier.height(56.dp))

                    Row {
                        ButtonIcon(
                            modifier = Modifier.weight(1f).padding(end = 5.dp),
                            color = Warna.MerahNormal,
                            icon = Icons.Default.Edit,
                            text = "Edit"
                        ) {
                            showDialog = true
                        }
                        ButtonIcon(
                            modifier = Modifier.weight(1f).padding(start = 5.dp),
                            color = Color.Red,
                            icon = Icons.AutoMirrored.Filled.Logout,
                            text = "Log out"
                        ) {
                            viewmodel.loginResponseReset()
                            navController.navigate(Screen.Login.route)
                        }
                    }


                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = stringResource(R.string.app_version),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )

                }


            }
        }
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

@Composable
fun EditProfilResponse(viewmodel:MahasiswaListViewModel, context: Context, onShowDialogChange: () -> Unit){

    when(val response = viewmodel.editMahasiswaResponse){
        is Response.Success -> {
            viewmodel.changeLoading(false)
            Toast.makeText(context, response.data, Toast.LENGTH_SHORT).show()
            onShowDialogChange()
            viewmodel.editMahasiswaResponseReset()
        }

        is Response.Failure -> {
            viewmodel.changeLoading(false)
            Log.d("waow", response.e.toString())
            Toast.makeText(context, response.e.toString(), Toast.LENGTH_SHORT).show()
            viewmodel.editMahasiswaResponseReset()

        }
        is Response.Loading -> {
            viewmodel.changeLoading(false)

        }
    }
}


