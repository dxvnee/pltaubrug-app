package org.d3if3121.absenubrugadmin.ui.screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.absenubrugadmin.ui.component.BottomBar
import org.d3if3121.absenubrugadmin.ui.component.TopBar
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrugadmin.data.model.Absen
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.navigation.Screen
import org.d3if3121.absenubrugadmin.ui.component.CardList
import org.d3if3121.absenubrugadmin.ui.component.DataKosong
import org.d3if3121.absenubrugadmin.ui.component.HeaderContent
import org.d3if3121.absenubrugadmin.ui.formula.filterAbsen



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EmployeePage(
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
                            top = paddingValues.calculateTopPadding() - 10.dp,
                            start = 17.dp,
                            end = 17.dp
                        )
                ) {
                    MainContentEmployee(
                        viewmodel = viewmodel,
                        navController = navController
                    )
                }

            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )

}


@Composable
fun MainContentEmployee(
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var masukpergi by remember { mutableStateOf("(Sudah Absen)") }
    var sudahabsen by remember { mutableStateOf(listOf<Mahasiswa>()) }

    viewmodel.getMahasiswaNip()
    val absenhariini = filterAbsen(viewmodel){ it.tanggal == viewmodel.tanggal }
    val belumabsen = viewmodel.mahasiswaList.minus(sudahabsen.toSet()).toMutableList()
    val mahasiswaList by remember { mutableStateOf(viewmodel.mahasiswaList) }


    HeaderContent(
        viewmodel = viewmodel,
        masukpergi = masukpergi,
        onclick1 = {
            masukpergi = "(Sudah Absen)"
        },
        onclick2 = {
            masukpergi = "(Belum Absen)"
        },
        judul = "Absensi",
        dropdowntext1 = "Sudah Absen",
        dropdowntext2 = "Belum Absen"
    )

    LazyColumn {
        when (masukpergi) {
            "(Sudah Absen)" -> {
                if (absenhariini.isEmpty()) {
                    item {
                        DataKosong()
                    }
                } else {
                    items(absenhariini) { absen ->

                        val mahasiswa = mahasiswaList.find { mahasiswa ->
                            mahasiswa.nip == absen.nip
                        }

                        CardList(absen, mahasiswa!!){
                            viewmodel.changeAbsen(absen)
                            navController.navigate(Screen.Project.route)
                        }
                        viewmodel.mahasiswaList.forEach { mahasiswa ->
                            if (mahasiswa.nip == absen.nip) {
                                sudahabsen = sudahabsen.plus(mahasiswa)
                            }
                        }
                    }
                }
            }

            "(Belum Absen)" -> {
                if (belumabsen.isEmpty()) {
                    item {
                        DataKosong()
                    }
                } else {
                    items(belumabsen) { mahasiswa ->
                        if(("UNKNOWN" !in mahasiswa.role) && ("ADMIN" !in mahasiswa.role)){
                            CardList(
                                Absen(
                                    nama = mahasiswa.nama,
                                    nip = mahasiswa.nip,
                                ), mahasiswa
                            ){}
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







