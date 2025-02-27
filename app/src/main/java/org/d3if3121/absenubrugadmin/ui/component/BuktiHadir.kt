package org.d3if3121.absenubrugadmin.ui.component

import android.net.Uri
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationServices
import org.d3if3121.absenubrugadmin.R
import org.d3if3121.absenubrugadmin.ui.formula.getCurrentLocation
import org.d3if3121.absenubrugadmin.ui.formula.isInsideRadiusAndroid
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel


@Composable
fun BuktiHadir(
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    absen: String,
    onUrlChange: (String?) -> Unit,
    onLokasiChange: (Double, Double) -> Unit,
    imageUrl: String?,
    onImageChange: (Uri) -> Unit,
    onClickDialogLokasi: () -> Unit,
    lokasi: String,
    sudahabsen: Boolean,
) {
    var context = LocalContext.current


    //GAMBAR
    var tombolGambar by remember { mutableStateOf(false) }

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
            getCurrentLocation(context, fusedLocationClient) { lat, lon ->
                location.value = Pair(lat, lon)
            }
        }
    }

    var konfirmasilokasi by remember { mutableStateOf(false) }

    if(!imageUrl.isNullOrEmpty()){
        tombolGambar = true
    }

    if (showDialogGambar) {
        imageUrl?.let {
            DialogGambar(
                showDialog = showDialogGambar,
                onDismissRequest = { showDialogGambar = false },
                imageUrl = it,
                onClick = { showDialogGambar = false }
            )
        }
    }


    if(showDialogLokasi){
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



    }
}