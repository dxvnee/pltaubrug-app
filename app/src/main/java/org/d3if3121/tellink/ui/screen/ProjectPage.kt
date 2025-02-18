package org.d3if3121.tellink.ui.screen

import android.annotation.SuppressLint
import android.net.Uri
import org.d3if3121.tellink.R
import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.tellink.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import org.d3if3121.tellink.components.LoadingIndicator
import org.d3if3121.tellink.core.printError
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response.Failure
import org.d3if3121.tellink.data.model.Response.Loading
import org.d3if3121.tellink.data.model.Response.Success
import org.d3if3121.tellink.ui.component.BottomBar
import org.d3if3121.tellink.ui.component.InputPutih
import org.d3if3121.tellink.ui.component.PilihanPutih
import org.d3if3121.tellink.ui.component.TambahProjectDialog
import org.d3if3121.tellink.ui.component.TopBar
import org.d3if3121.tellink.ui.component.cekScroll
import org.d3if3121.tellink.ui.component.uploadImageToFirebase
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.ui.viewmodel.ProjectListViewModel
import java.io.File
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Preview(showBackground = true)
@Composable
fun ProjectPagePreview() {
    ProjectPage(navController = rememberNavController())
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectPage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()

    val user = viewmodel.user

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            projectviewmodel.getProjectListByNim(user.nim)
            when(val projectListByNimResponse = projectviewmodel.projectListByNimResponse){
                is Loading -> LoadingIndicator()
                is Success -> projectListByNimResponse.data.let { projectList ->
                    Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                        MainContentProject(
                            lazyListState = lazyListState,
                            paddingValues = paddingValues,
                            projectList = projectList!!,
                            viewmodel = viewmodel,
                            projectviewmodel = projectviewmodel,
                            navController = navController
                        )
                    }
                }
                is Failure -> printError(projectListByNimResponse.e)
            }


        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )


}




@Composable
fun MainContentProject(
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    projectList: List<Project>,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val user = viewmodel.user
    val numbers = remember { List(size = 200){ it } }
    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
        )
    )
    var search by remember { mutableStateOf("") }
    var showDialog = remember { mutableStateOf(false) }
    var refreshData = remember { mutableStateOf(false) }
    var secondmode by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val storage = FirebaseStorage.getInstance().reference
    val context = LocalContext.current

    @Composable
    fun UploadImageComponent() {
        val context = LocalContext.current
        val storage = FirebaseStorage.getInstance().reference
        val imageUri = remember { mutableStateOf<Uri?>(null) }

        // Membuat tempat penyimpanan sementara untuk gambar yang diambil
        val tempUri = remember {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                File.createTempFile("temp_image", ".jpg", context.cacheDir)
            )
        }

        val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri.value = tempUri
                uploadImageToFirebase(
                    tempUri,
                    onSuccess = { url ->
                        Log.d("Firebase", "Upload berhasil: $url")
                        // TODO: Simpan URL ke database atau lakukan tindakan lainnya
                    },
                    onFailure = { e ->
                        Log.e("Firebase", "Gagal mengunggah: ${e.message}")
                    }
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Button(onClick = { cameraLauncher.launch(tempUri) }) {
                Text("Ambil Foto")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Menampilkan gambar yang diambil
            imageUri.value?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Gambar yang diunggah",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }




    LaunchedEffect(refreshData) {
        if (refreshData.value) {
            projectviewmodel.getProjectList()
        }
    }

    TambahProjectDialog(showDialog, refreshData, viewmodel, projectviewmodel)

    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp).background(color = Warna.PutihNormal)
    ) {

        LazyColumn(
            modifier = Modifier.padding(top = padding).fillMaxWidth().fillMaxHeight(),
            state = lazyListState
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Absensi",
                    color = Warna.MerahNormal,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,

                    )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 17.dp)

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                    }


                }


            }
            if (projectList.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),

                        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
                        elevation = CardDefaults.cardElevation(20.dp),
                        shape = RoundedCornerShape(15.dp)
                    ){
                        Column(
                            modifier = Modifier.padding(17.dp)
                        ){
                            Text(
                                text = stringResource(id = R.string.bukti_hadir),
                                color = Warna.MerahNormal,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            InputPutih(
                                input = "",
                                placeholder = stringResource(id = R.string.enter_nim),
                                onInputChange = { input ->

                                },
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.fillMaxWidth()

                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            PilihanPutih(
                                text1 = "Upload Foto",
                                text2 = "Upload Lokasi",
                                condition = secondmode,
                                color1 = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                                color2 = ButtonDefaults.buttonColors(containerColor = Warna.PutihNormal),
                                onclick1 = {
                                    launcher.launch("image/*") // Pilih gambar dari galeri
                                },
                                onclick2 = {
                                    secondmode = true
                                }
                            )

                            // State untuk menyimpan status yang dipilih
                            var selectedStatus by remember { mutableStateOf("Pilih Keterangan") }

                            Text(
                                text = "Keterangan: $selectedStatus",
                                color = Warna.MerahNormal,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            // Tombol untuk memilih status
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                            ) {
                                Button(
                                    onClick = { selectedStatus = "Hadir" },
                                    colors = ButtonDefaults.buttonColors(containerColor = Warna.Hijau),
                                    modifier = Modifier.padding(end = 8.dp).weight(1f),
                                    shape = RoundedCornerShape(7.dp)
                                ) {
                                    Text(text = "Hadir")
                                }
                                Button(
                                    onClick = { selectedStatus = "Izin" },
                                    colors = ButtonDefaults.buttonColors(containerColor = Warna.Kuning),
                                    modifier = Modifier.padding(end = 8.dp).weight(1f),
                                    shape = RoundedCornerShape(7.dp)

                                ) {
                                    Text(text = "Izin")
                                }
                                Button(
                                    onClick = { selectedStatus = "Tidak Masuk" },
                                    colors = ButtonDefaults.buttonColors(containerColor = Warna.Merah),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(7.dp)
                                ) {
                                    Text(text = "Tidak Masuk")
                                }
                            }
                            Text(
                                text = stringResource(id = R.string.deskripsi),
                                color = Warna.MerahNormal,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            InputPutih(
                                input = "",
                                placeholder = stringResource(id = R.string.masuk_deskripsi),
                                onInputChange = { input ->

                                },
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.fillMaxWidth()

                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            // Menampilkan status yang dipilih

                            Spacer(modifier = Modifier.height(16.dp))

// Tombol Kirim
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        Toast.makeText(context, "Absen Berhasil", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Warna.MerahNormal),
                                    shape = RoundedCornerShape(7.dp),
                                    modifier = Modifier.width(150.dp) // Atur lebar tombol sesuai kebutuhan
                                ) {
                                    Text(text = "Kirim", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize().height(550.dp)
                    )
                    {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Project doesn't exist!",
                                color = Warna.MerahNormal,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }

                    }


                }



            }
        }
    }
}



fun formatRelativeTime(dateTimeString: String): String {
    if (dateTimeString == "") {
        return "unknown time"
    } else {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val parsedDateTime = LocalDateTime.parse(dateTimeString, formatter)

        val now = LocalDateTime.now(ZoneId.systemDefault())
        val duration = Duration.between(parsedDateTime, now)

        return when {
            duration.toMinutes() < 1 -> "just now"
            duration.toHours() < 1 -> "${duration.toMinutes()} minutes ago"
            duration.toDays() < 1 -> "${duration.toHours()} hours ago"
            duration.toDays() < 7 -> "${duration.toDays()} days ago"
            duration.toDays() < 30 -> "${duration.toDays() / 7} weeks ago"
            duration.toDays() < 365 -> "${duration.toDays() / 30} months ago"
            else -> "${duration.toDays() / 365} years ago"
        }
    }


}





