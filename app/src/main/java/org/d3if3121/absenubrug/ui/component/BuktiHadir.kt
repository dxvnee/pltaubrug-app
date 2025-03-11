package org.d3if3121.absenubrug.ui.component

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationServices
import org.d3if3121.absenubrug.R
import org.d3if3121.absenubrug.ui.formula.getCurrentLocation
import org.d3if3121.absenubrug.ui.formula.isInsideRadiusAndroid
import org.d3if3121.absenubrug.ui.theme.Warna
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel


@Composable
fun BuktiHadir(
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    absen: String,
    onUriChange: (Uri?) -> Unit,
    onLokasiChange: (Double, Double) -> Unit,
    imageUri: Uri?,
    onImageChange: (Uri) -> Unit,
    onClickDialogLokasi: () -> Unit,
    lokasi: String,
    sudahabsen: Boolean,
    statushadir: Boolean,
    imageUrl: String?,
    selectedstatus: String
) {
    var context = LocalContext.current

    DialogLoading(viewmodel)

    //GAMBAR
    var tombolGambar by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onUriChange(uri)
    }
    var showDialogGambar by remember { mutableStateOf(false) }


    //LOKASI
    val location = remember { mutableStateOf<Pair<Double, Double>?>(null) }

    var lokasilat by remember { mutableStateOf(0.0) }
    var lokasilon by remember { mutableStateOf(0.0) }
    var showDialogLokasi by remember { mutableStateOf(false) }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation(context, fusedLocationClient, viewmodel) { lat, lon ->
                location.value = Pair(lat, lon)
            }
        }
    }

    var konfirmasilokasi by remember { mutableStateOf(false) }

    if(imageUrl != ""){
        tombolGambar = true
    }
    if(showDialogGambar){
        DialogGambar(
            onDismissRequest = {
                showDialogGambar = false
            },
            imageUri = imageUri,
            onClick = {
                showDialogGambar = false
            },
            imageUrl = imageUrl
        )
    }

    if(showDialogLokasi){
        Log.d("herh", "herh")
        konfirmasilokasi = isInsideRadiusAndroid(lokasilat,  lokasilon)
        DialogLokasi(
            konfirmasilokasi = konfirmasilokasi,
            onDismissRequest = { showDialogLokasi = false },
            lokasi = lokasi,
            onClick1 = {
                onClickDialogLokasi()
                tombolGambar = false
                showDialogLokasi = false
                location.value = null
            },
            onClick2 = {
                location.value = null
                showDialogLokasi = false
            },

            )
    }


    Column {
        Text(
            text = stringResource(id = R.string.bukti_hadir),
            color = Warna.MerahNormal,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        InputPutih(
            input = absen,
            placeholder = "Bukti hadir...",
            onInputChange = { input ->

            },
            keyboardType = KeyboardType.Number,
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (tombolGambar){
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        showDialogGambar = true
                    },
                horizontalArrangement = Arrangement.End,
            ){
                Text(
                    text = "Lihat Foto",
                    color = Warna.MerahNormal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))


        if (sudahabsen){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if(statushadir){
                    Button(
                        onClick = {
                            Log.d("masuk", "masuk")

                            if (ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                getCurrentLocation(context, fusedLocationClient, viewmodel) { lat, lon ->
                                    location.value = Pair(lat, lon)
                                }
                                Log.d("masuk", location.value.toString())

                            } else {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                Log.d("masuk", "masuk2")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 5.dp)
                    ) {
                        Text(text = "Lokasi", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Log.d("masuk", location.value.toString())

                    LaunchedEffect(location.value) {
                        location.value?.let { (lat, lon) ->
                            lokasilat = lat
                            lokasilon = lon
                            showDialogLokasi = true
                            onLokasiChange(lat, lon)
                            Log.d("masuk", "masuk5")

                        }
                    }

                }

                if(selectedstatus != "Pilih Keterangan"){
                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = 5.dp, bottom = 16.dp)
                    ) {
                        Text(text = "Foto", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }


                    imageUri?.let {
                        onImageChange(it)
                        tombolGambar = true
                    }
                }


            }
        }

    }
}

