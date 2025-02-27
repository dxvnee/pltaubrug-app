package org.d3if3121.absenubrug.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.absenubrug.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Job
import org.d3if3121.absenubrug.ui.component.BottomBar
import org.d3if3121.absenubrug.ui.component.InputPutih
import org.d3if3121.absenubrug.ui.component.TopBar
import org.d3if3121.absenubrug.ui.component.cekScroll
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.absenubrug.R
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.ImageUpload
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.navigation.Screen
import org.d3if3121.absenubrug.ui.component.BuktiHadir
import org.d3if3121.absenubrug.ui.component.ButtonKecil
import org.d3if3121.absenubrug.ui.component.ButtonTiga
import org.d3if3121.absenubrug.ui.component.CardList
import org.d3if3121.absenubrug.ui.component.DataDua
import org.d3if3121.absenubrug.ui.component.DataKosong
import org.d3if3121.absenubrug.ui.component.DialogGambar
import org.d3if3121.absenubrug.ui.component.DialogLoading
import org.d3if3121.absenubrug.ui.component.DialogLokasi
import org.d3if3121.absenubrug.ui.component.HeaderContent
import org.d3if3121.absenubrug.ui.formula.filterAbsen






@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EmployeePage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel,
    tanggal: String = ""
) {
    val lazyListState = rememberLazyListState()
    val user = viewmodel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding() -10.dp, start = 17.dp, end = 17.dp)
                    .background(color = Warna.PutihNormal).fillMaxHeight()
            ){
                MainContentEmployee(
                    viewmodel = viewmodel,
                    navController = navController
                )
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
    var absenhariini = filterAbsen(viewmodel){ it.tanggal == viewmodel.tanggal }
    var belumabsen = viewmodel.mahasiswaList.minus(sudahabsen.toSet()).toMutableList()


    HeaderContent(
        viewmodel = viewmodel,
        masukpergi = masukpergi,
        onclick1 = {
            masukpergi = "(Sudah Absen)"
        },
        onclick2 = {
            masukpergi = "(Belum Absen)"
        }
    )

    LazyColumn {
        when (masukpergi) {
            "(Sudah Absen)" -> {
                if (absenhariini.isNullOrEmpty()) {
                    item {
                        DataKosong()
                    }
                } else {
                    items(absenhariini) { absen ->
                        CardList(absen){
                            viewmodel.changeAbsen(absen)
                            navController.navigate(Screen.Project.route)
                        }
                        viewmodel.mahasiswaList.forEach { mahasiswa ->
                            if (mahasiswa.nim == absen.nip) {
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
                        CardList(
                            Absen(
                                nama = mahasiswa.nama,
                                nip = mahasiswa.nim,
                            )
                        ){

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







