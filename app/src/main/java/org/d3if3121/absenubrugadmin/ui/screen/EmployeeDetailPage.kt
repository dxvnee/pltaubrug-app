package org.d3if3121.absenubrugadmin.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.absenubrugadmin.ui.component.BottomBar
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.data.model.Response
import org.d3if3121.absenubrugadmin.navigation.Screen
import org.d3if3121.absenubrugadmin.ui.component.CardListPegawai
import org.d3if3121.absenubrugadmin.ui.component.DataKosong
import org.d3if3121.absenubrugadmin.ui.component.DialogRole
import org.d3if3121.absenubrugadmin.ui.component.HeaderContent



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EmployeeDetailPage(
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
            ){
                Column(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding(), start = 17.dp, end = 17.dp)
                ){
                    MainContentEmployeeDetail(
                        viewmodel = viewmodel,
                        navController = navController
                    )
                }

            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    )

}


@Composable
fun MainContentEmployeeDetail(
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    navController: NavHostController
) {

    var dialogrole by remember { mutableStateOf(false) }
    var statuspage by remember { mutableStateOf("(Daftar Pegawai)") }
    viewmodel.getMahasiswaNip()


    val listpegawai by remember { derivedStateOf { (viewmodel.mahasiswaList) }}
    var currentpegawai by remember { mutableStateOf(Mahasiswa()) }

    LaunchedEffect (Unit){
        viewmodel.getMahasiswaList()
    }

    EmployeeDetailResponse(viewmodel){
        dialogrole = it
    }

    DialogRole(
        viewmodel = viewmodel,
        onDismissRequest = {
            dialogrole = false
        },
        dialogrole = dialogrole,
        pegawai = currentpegawai,
    ){  pegawai ->
        viewmodel.changePegawai(pegawai)
        dialogrole = false
        navController.navigate(Screen.EmployeeHome.route)
    }

    HeaderContent(
        viewmodel = viewmodel,
        masukpergi = statuspage,
        onclick1 = {
            statuspage = "(Daftar Pegawai)"
        },
        onclick2 = {
            statuspage = "(Pengajuan)"
        },
        judul = "Pegawai",
        dropdowntext1 = "Daftar Pegawai",
        dropdowntext2 = "Pengajuan",
        matikantanggal = true
    )

    LazyColumn {
        when (statuspage) {
            "(Daftar Pegawai)" -> {
                if (!listpegawai.any{ "UNKNOWN" !in it.role }) {
                    item {
                        DataKosong()
                    }
                } else {
                    items(listpegawai) { pegawai ->
                        if(("UNKNOWN" !in pegawai.role) && ("ADMIN" !in pegawai.role)) {
                            CardListPegawai(pegawai){
                                currentpegawai = pegawai
                                dialogrole = true
                            }
                        }
                    }
                }
            }

            "(Pengajuan)" -> {
                if (!listpegawai.any { "UNKNOWN" in it.role }) {
                    item {
                        DataKosong()
                    }
                } else {

                    items(listpegawai) { pegawai ->
                        if("UNKNOWN" in pegawai.role) {
                            CardListPegawai(pegawai){
                                currentpegawai = pegawai
                                dialogrole = true
                            }
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(130.dp))
        }
    }
}


@Composable
fun EmployeeDetailResponse(
    viewmodel: MahasiswaListViewModel,
    onDialogroleChange: (Boolean) -> Unit
){
    val context = LocalContext.current
    when(val response = viewmodel.editRoleResponse){
        is Response.Success -> {
            Toast.makeText(context, "Role berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            viewmodel.changeLoading(false)
            viewmodel.editRoleResponseReset()
            viewmodel.getMahasiswaList()

            onDialogroleChange(false)


        }
        is Response.Failure -> {
            Toast.makeText(context, response.e.toString(), Toast.LENGTH_SHORT).show()
            Log.e("eror", response.e.toString())
        }
        is Response.Loading -> {}
    }
}






