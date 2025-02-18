package org.d3if3121.AdminAbsen.ui.screen
import android.annotation.SuppressLint
import android.net.Uri
import org.d3if3121.AdminAbsen.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.AdminAbsen.navigation.Screen
import org.d3if3121.AdminAbsen.ui.theme.TellinkTheme
import org.d3if3121.AdminAbsen.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierInfo
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.d3if3121.AdminAbsen.navigation.BottomBarScreen
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardElevation
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.d3if3121.AdminAbsen.components.LoadingIndicator
import org.d3if3121.AdminAbsen.core.printError
import org.d3if3121.AdminAbsen.data.model.Mahasiswa
import org.d3if3121.AdminAbsen.data.model.Project
import org.d3if3121.AdminAbsen.data.model.Response.Failure
import org.d3if3121.AdminAbsen.data.model.Response.Loading
import org.d3if3121.AdminAbsen.data.model.Response.Success
import org.d3if3121.AdminAbsen.ui.component.BottomBar
import org.d3if3121.AdminAbsen.ui.component.InputPutih
import org.d3if3121.AdminAbsen.ui.component.PilihanPutih
import org.d3if3121.AdminAbsen.ui.component.TopBar
import org.d3if3121.AdminAbsen.ui.component.cekScroll
import org.d3if3121.AdminAbsen.ui.component.uploadImageToFirebase
import org.d3if3121.AdminAbsen.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.AdminAbsen.ui.viewmodel.ProjectListViewModel

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Duration



@Composable
fun ProfilePage(
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
            projectviewmodel.getProjectListByNim(user.nip)
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

    @Composable
    fun UploadImageComponent() {
        // Menggunakan remember untuk menyimpan nilai uri image
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        // Membuat launcher untuk memilih file
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri // Menyimpan URI gambar yang dipilih
            if (imageUri != null) {
                uploadImageToFirebase(
                    imageUri!!,
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

        // Button untuk membuka galeri
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Pilih Gambar")
        }

        // Menampilkan gambar jika ada
        imageUri?.let {
            Image(painter = rememberAsyncImagePainter(it), contentDescription = "Uploaded Image")
        }
    }



    LaunchedEffect(refreshData) {
        if (refreshData.value) {
            projectviewmodel.getProjectList()
        }
    }

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
                    text = "Edit Absensi",
                    color = Warna.Biru,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 4.dp)
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
                                color = Warna.Biru,
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
                                color1 = ButtonDefaults.buttonColors(containerColor = Warna.Biru),
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
                                color = Warna.Biru,
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
                                color = Warna.Biru,
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
                                // Tombol Simpan dan Hapus
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        onClick = {
                                            // TODO: Tambahkan aksi untuk menyimpan data absensi
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Warna.Biru), // Warna Biru untuk simpan
                                        shape = RoundedCornerShape(7.dp),
                                        modifier = Modifier.width(150.dp) // Atur lebar tombol
                                    ) {
                                        Text(text = "Simpan", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            // TODO: Tambahkan aksi untuk menghapus data absensi
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Warna.Merah), // Warna merah untuk hapus
                                        shape = RoundedCornerShape(7.dp),
                                        modifier = Modifier.width(150.dp) // Atur lebar tombol
                                    ) {
                                        Text(text = "Hapus", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    }
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
                                color = Warna.Biru,
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






