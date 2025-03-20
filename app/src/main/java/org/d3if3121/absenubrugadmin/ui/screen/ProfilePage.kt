package org.d3if3121.absenubrugadmin.ui.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.R
import org.d3if3121.absenubrugadmin.ui.component.BottomBar
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrugadmin.data.model.Response
import org.d3if3121.absenubrugadmin.navigation.Screen
import org.d3if3121.absenubrugadmin.ui.component.ButtonIcon
import org.d3if3121.absenubrugadmin.ui.component.DialogEditProfile
import org.d3if3121.absenubrugadmin.ui.component.DialogLoading
import org.d3if3121.absenubrugadmin.ui.component.FotoProfil
import org.d3if3121.absenubrugadmin.ui.component.KartuProfil


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfilePage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel,
) {
    val lazyListState = rememberLazyListState()
    val user = viewmodel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
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
                    ProfilePageContent(paddingValues, viewmodel, navController)
                }

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

    LaunchedEffect(Unit){
        viewmodel.getMahasiswa(viewmodel.user.nip)
    }


    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog){
        DialogEditProfile(pegawai = viewmodel.user, viewmodel = viewmodel, onDismissRequest = { showDialog = false })
    }
    FotoProfilResponse(viewmodel, context)
    EditProfilResponse(viewmodel, context){
        showDialog = false
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues).padding(top = 30.dp, start = 17.dp, end = 17.dp),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column (
            modifier = Modifier.padding(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            KartuProfil(
                pegawai = viewmodel.user,
                viewmodel = viewmodel,
                button1text = "Edit",
                button2text = "Log out",
                button1color = Warna.MerahNormal,
                button2color = Color.Red,
                button1icon = Icons.Default.Edit,
                button2icon = Icons.AutoMirrored.Filled.Logout,
                onClick1 = { showDialog = true },
                onClick2 = {
                    viewmodel.loginResponseReset()
                    navController.navigate(Screen.Login.route)
                }
            )
            Text(
                text = stringResource(R.string.app_version),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }



}

@Composable
fun FotoProfilResponse(viewmodel: MahasiswaListViewModel, context: Context){
    when(val response = viewmodel.addFotoProfil){
        is Response.Success -> {
            viewmodel.changeLoading(false)
            viewmodel.addFotoProfilReset()
            Toast.makeText(context, "Upload foto berhasil!", Toast.LENGTH_SHORT).show()
        }
        is Response.Failure -> {
            Toast.makeText(context, response.e.toString(), Toast.LENGTH_SHORT).show()
        }
        is Response.Loading -> {}
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



