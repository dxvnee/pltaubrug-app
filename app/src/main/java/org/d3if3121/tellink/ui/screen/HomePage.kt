package org.d3if3121.tellink.ui.screen

import android.annotation.SuppressLint
import org.d3if3121.tellink.R
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.theme.TellinkTheme
import org.d3if3121.tellink.ui.theme.Warna
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
import org.d3if3121.tellink.navigation.BottomBarScreen
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardElevation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import org.d3if3121.tellink.components.LoadingIndicator
import org.d3if3121.tellink.core.printError
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response.Success
import org.d3if3121.tellink.data.model.Response.Loading
import org.d3if3121.tellink.data.model.Response.Failure
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.ui.component.BottomBar
import org.d3if3121.tellink.ui.component.InputPutihSearch
import org.d3if3121.tellink.ui.component.KartuKonten
import org.d3if3121.tellink.ui.component.TopBar
import org.d3if3121.tellink.ui.component.cekScroll
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.ui.viewmodel.ProjectListViewModel


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(navController = rememberNavController())
}


val TOP_BAR_HEIGHT = 70.dp



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    var user = viewModel.user

    Scaffold(
        topBar = {
            Log.d("usersekaranghome", user.toString())

            TopBar(lazyListState = lazyListState, helloActive = true, navController = navController, user = user)
        },
        content = { paddingValues ->
            projectviewmodel.getProjectListUser(user.nim)
            when(val projectListUserResponse = projectviewmodel.projectListUserResponse){
                is Loading -> LoadingIndicator()
                is Success -> projectListUserResponse.data.let { projectList ->

                        Log.d("HASILNYA", projectList.toString())
                        Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                            MainContentHome(
                                navController = navController,
                                lazyListState = lazyListState,
                                paddingValues = paddingValues,
                                viewmodel = viewModel,
                                projectviewmodel = projectviewmodel,
                                projectList = projectList!!,
                                user = user
                            )
                        }

                }
                is Failure -> printError(projectListUserResponse.e)
            }

        },
        bottomBar = {
            BottomBar(navController = navController, home = true){
//                viewModel.markProject(user.nim)
            }
        },
        contentColor = Warna.PutihNormal
    )
}


@Composable
fun MainContentHome(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: MahasiswaListViewModel,
    projectviewmodel: ProjectListViewModel,
    projectList: List<Project>,
    viewModel: MahasiswaListViewModel = hiltViewModel(),
    user: Mahasiswa
) {

    var context = LocalContext.current
    var request = remember { mutableStateOf(false) }
    when(val addRequestResponse = projectviewmodel.addRequestResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Request Success!", Toast.LENGTH_SHORT).show()
            projectviewmodel.resetAddRequestResponse()
        }
        is Failure -> printError(addRequestResponse.e)
    }
    when(val deleteRequestResponse = projectviewmodel.deleteRequestResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Request Cancelled.", Toast.LENGTH_SHORT).show()
            projectviewmodel.resetDeleteRequestResponse()
        }
        is Failure -> printError(deleteRequestResponse.e)
    }


    val numbers = remember { List(size = 200){ it } }
    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
            )
    )
    var search by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp)
    ) {

        LazyColumn(
            modifier = Modifier.padding(top = padding).fillMaxWidth().fillMaxHeight()
                .background(color = Warna.PutihNormal),
            state = lazyListState
        ){
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Welcome to Tellink",
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

                ){
//                    InputPutihSearch(
//                        input = search,
//                        placeholder = stringResource(id = R.string.search),
//                        onInputChange = { input ->
//                            search = input
//                        },
//                        keyboardType = KeyboardType.Number,
//                        modifier = Modifier.fillMaxWidth()
//                    )
                }
            }
            if (projectList.isEmpty()) {
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize().height(550.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "You're up to date!",
                                color = Warna.MerahNormal,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }

                    }
                }
            } else {
                items(
                    items = projectList,
                ){ project ->
                    val mahasiswa = viewmodel.mahasiswaMap[project.nim] ?: Mahasiswa()
                    var requestornot by remember { mutableStateOf(false) }

                    LaunchedEffect(project.id, user.nim) {
                        requestornot = viewmodel.checkRequestProject(project.id!!, user.nim).await()
                    }


                    LaunchedEffect(project.nim) {
                        viewmodel.getMahasiswaByNim(project.nim)
                        viewmodel.addViewedProject(project.id!!)

                    }

                    KartuKonten(
                        fotoprofil = R.drawable.photo,
                        nama = mahasiswa.nama,
                        jurusan = mahasiswa.jurusan,
                        hari = formatRelativeTime(project.date!!),

                        judul = project.title,
                        gambar = project.image ?: "",
                        konten = project.desc,
                        request = requestornot,
                        requests = project.requests!!,
                        tag = project.tag,
                        onrequestchangefalse = {
                            requestornot = false

                        },
                        onrequestchangetrue = {
                            requestornot = true

                        },
                        onclick = {
                            projectviewmodel.addRequest(project.id!!, user.nim)
                        },
                        onclickcancel = {
                            projectviewmodel.deleteRequest(project.id!!, user.nim)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }



        }
    }

}


