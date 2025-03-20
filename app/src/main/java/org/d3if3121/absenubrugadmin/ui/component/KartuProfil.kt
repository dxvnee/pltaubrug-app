package org.d3if3121.absenubrugadmin.ui.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrugadmin.data.model.Mahasiswa
import org.d3if3121.absenubrugadmin.navigation.Screen
import org.d3if3121.absenubrugadmin.ui.theme.Warna
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel

@Composable
fun KartuProfil(
    viewmodel: MahasiswaListViewModel,
    pegawai: Mahasiswa,

    button1text: String,
    button2text: String,
    button1color: Color,
    button2color: Color,
    button1icon: ImageVector,
    button2icon: ImageVector,

    onClick1: () -> Unit,
    onClick2: () -> Unit,

){
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        Log.d("WHY", imageUri.toString())
        imageUri?.let {
            viewmodel.addFotoProfil(pegawai.nip, imageUri!!)
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FotoProfil(
            imageUrl = pegawai.foto,
            modifier = Modifier

                .size(145.dp).clickable {
                    launcher.launch("image/*")
                }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = pegawai.nama,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Row{
            Text(
                text = pegawai.nip + " - ",
                fontSize = 16.sp,
                color = Warna.HitamNormal
            )
            Text(
                text = pegawai.posisi,
                fontSize = 16.sp,
                color = Warna.HitamNormal
            )
        }


        pegawai.role.forEach {
            Text(
                text = it,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row {
            ButtonIcon(
                modifier = Modifier.weight(1f).padding(end = 5.dp),
                color = button1color,
                icon = button1icon,
                text = button1text
            ) { onClick1() }

            ButtonIcon(
                modifier = Modifier.weight(1f).padding(start = 5.dp),
                color = button2color,
                icon = button2icon,
                text = button2text
            ) { onClick2() }
        }


    }

}

@Composable
fun DialogProfil(
    pegawai: Mahasiswa,
    viewmodel: MahasiswaListViewModel,
    onDismissRequest: () -> Unit,

    onClick1: () -> Unit,
    onClick2: () -> Unit,

){
    LaunchedEffect(viewmodel.currentpegawaiedit){
        viewmodel.getMahasiswaByNip(pegawai.nip)
        Log.d("pegawe", viewmodel.currentpegawaiedit.toString())
    }



    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Informasi Pegawai", color = Warna.MerahNormal, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            KartuProfil(
                viewmodel = viewmodel,
                pegawai = viewmodel.currentpegawaiedit,
                button1text = "Edit",
                button2text = "Cek Absen",
                button1color = Warna.MerahNormal,
                button2color = Warna.MerahNormal,
                button1icon = Icons.Default.Edit,
                button2icon = Icons.Default.Checklist,
                onClick1 = onClick1,
                onClick2 = onClick2
            )
        },
        confirmButton = {},
        containerColor = Warna.PutihNormal,
        shape = RoundedCornerShape(20.dp)
    )



}