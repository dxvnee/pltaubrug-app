package org.d3if3121.pltaconnect.ui.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.d3if3121.pltaconnect.R
import org.d3if3121.pltaconnect.core.printError
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import org.d3if3121.pltaconnect.ui.viewmodel.ProjectListViewModel

    
@Composable
fun ConfirmKonten(
    navController: NavController,
    projectId: String,
    viewmodel: PegawaiListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
){
    var refreshKey by remember { mutableStateOf(0) }

    LaunchedEffect(refreshKey) {
        projectviewmodel.getProjectById(projectId)
    }
    Log.d("idproject", projectId)
    projectviewmodel.getProjectById(projectId)
    var project = projectviewmodel.project

    var judul by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf(listOf<String>()) }

    var secondmode by remember { mutableStateOf(false) }



    val context = LocalContext.current


    when(val updateProjectResponse = projectviewmodel.updateProjectResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Edit Success!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Project.route)
        }
        is Failure -> printError(updateProjectResponse.e)
    }

    when(val deleteProjectResponse = projectviewmodel.deleteProjectResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Delete Success!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Project.route)
        }
        is Failure -> printError(deleteProjectResponse.e)
    }

    when(val addAcceptResponse = projectviewmodel.addAcceptResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Accepted!", Toast.LENGTH_SHORT).show()
            projectviewmodel.resetAddAcceptResponse()
            refreshKey++
        }
        is Failure -> printError(addAcceptResponse.e)
    }

    when(val deleteAcceptResponse = projectviewmodel.deleteAcceptResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show()
            projectviewmodel.resetDeleteRequestResponse()
            refreshKey++
        }
        is Failure -> printError(deleteAcceptResponse.e)
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 17.dp, end = 17.dp, bottom = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        PilihanPutih(
            text1 = "Accepted",
            text2 = "Requested",
            condition = secondmode,
            color1 = if(secondmode) {
                ButtonColors(
                    containerColor =  Warna.PutihNormal,
                    contentColor = Warna.MerahNormal,
                    disabledContentColor = Warna.MerahNormal,
                    disabledContainerColor = Warna.MerahNormal
                )
            } else {
                ButtonColors(
                    containerColor =  Warna.MerahNormal,
                    contentColor = Warna.MerahNormal,
                    disabledContentColor = Warna.MerahNormal,
                    disabledContainerColor = Warna.PutihNormal
                )
            },
            color2 = if(secondmode) {
                ButtonColors(
                    containerColor =  Warna.MerahNormal,
                    contentColor = Warna.MerahNormal,
                    disabledContentColor = Warna.MerahNormal,
                    disabledContainerColor = Warna.MerahNormal
                )
            } else {
                ButtonColors(
                    containerColor =  Warna.PutihNormal,
                    contentColor = Warna.MerahNormal,
                    disabledContentColor = Warna.PutihNormal,
                    disabledContainerColor = Warna.MerahNormal
                )
            },
            onclick1 = {
                secondmode = false
            },
            onclick2 = {
                secondmode = true
            }
        )
        LazyColumn(

        ) {
            if (secondmode == false){
                items(
                    items = project.requests!!,
                    key = { it }
                ) { nim ->

                    val pegawai = viewmodel.pegawaiMapProfile[nim] ?: Pegawai()

                    LaunchedEffect(nim) {
                        viewmodel.getPegawaiByNimProfile(nim)
                    }

                    KartuProfilPutih(
                        fotoprofil = R.drawable.photo,
                        nama = pegawai.nama,
                        nim = pegawai.nim,
                        jurusan = pegawai.jurusan,

                        onclick = {
                            projectviewmodel.addAccept(projectId, nim)
                        }
                    )
                }
            } else {
                items(
                    items = project.accept!!,
                    key = { it }
                ) { nim ->

                    val pegawai = viewmodel.pegawaiMapProfile[nim] ?: Pegawai()

                    LaunchedEffect(nim) {
                        viewmodel.getPegawaiByNimProfile(nim)
                    }

                    KartuProfilPutih(
                        fotoprofil = R.drawable.photo,
                        nama = pegawai.nama,
                        nim = pegawai.nim,
                        jurusan = pegawai.jurusan,
                        accepted = true,

                        onclickkick = {
                            projectviewmodel.deleteAccept(projectId, nim)
                        }
                    )
                }
            }


        }

    }





}
