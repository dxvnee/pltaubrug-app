package org.d3if3121.pltaconnect.ui.screen

import org.d3if3121.pltaconnect.R
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.theme.Warna
import androidx.compose.runtime.*
import androidx.compose.material3.ButtonColors
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.pltaconnect.components.LoadingIndicator
import org.d3if3121.pltaconnect.core.printError
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.Project
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.ui.component.BottomBar
import org.d3if3121.pltaconnect.ui.component.InputPutihSearch
import org.d3if3121.pltaconnect.ui.component.KartuKonten
import org.d3if3121.pltaconnect.ui.component.PilihanPutih
import org.d3if3121.pltaconnect.ui.component.TambahProjectDialog
import org.d3if3121.pltaconnect.ui.component.TopBar
import org.d3if3121.pltaconnect.ui.component.cekScroll
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import org.d3if3121.pltaconnect.ui.viewmodel.ProjectListViewModel
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
    viewmodel: PegawaiListViewModel = hiltViewModel(),
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
    viewmodel: PegawaiListViewModel = hiltViewModel(),
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
        ){
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "My Project",
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
                    InputPutihSearch(
                        input = search,
                        placeholder = stringResource(id = R.string.search),
                        onInputChange = { input ->
                            search = input
                        },
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.width(297.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Button(
                            modifier = Modifier.size(49.dp).fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonColors(
                                containerColor =  Warna.MerahNormal,
                                contentColor = Warna.PutihNormal,
                                disabledContentColor = Warna.MerahNormal,
                                disabledContainerColor = Warna.PutihNormal
                            ),
                            onClick = {
                                showDialog.value = true
                            },
                        ) {
                            Text(
                                modifier = Modifier.offset(-5.dp),
                                text = "+",
                                color = Warna.PutihNormal,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }


                }

                PilihanPutih(
                    text1 = "My Project",
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

            }
            if (projectList.isEmpty()) {
                item {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize().height(550.dp)
                    ){
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "Project doesn't exist!",
                                color = Warna.MerahNormal,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }

                    }
                }
            } else {
                if(!secondmode){
                    items(items = projectList){ project ->

                        val pegawai = viewmodel.pegawaiMap[project.nim] ?: Pegawai()

                        LaunchedEffect(project.nim) {
                            viewmodel.getPegawaiByNim(project.nim)
                        }

                        KartuKonten(
                            fotoprofil = R.drawable.photo,
                            nama = pegawai.nama,
                            jurusan = pegawai.jurusan,
                            hari =  formatRelativeTime(project.date!!),
                            type = "project",
                            tag = project.tag,
                            judul = project.title,
                            gambar = project.image ?: "",
                            konten = project.desc,
                            onclick = {
                                navController.navigate("${Screen.EditProject.route}/${project.id}")
                            },
                            onclicktext = {
                                navController.navigate("${Screen.ConfirmPage.route}/${project.id}")
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                } else {
                    Log.d("tes1", user.accept!!.toString())
                    items(
                        items = user.accept!!

                    ){ projectId ->
                        Log.d("tes5", "ererer")
                        projectviewmodel.getProjectById(projectId)

                        var project by remember { mutableStateOf(Project()) }
                        var pegawai by remember { mutableStateOf(Pegawai()) }
                        var requestornot by remember { mutableStateOf(false) }

                        LaunchedEffect(projectId) {
                            project = projectviewmodel.getProjectByIdSuspend(projectId)

                            pegawai = viewmodel.getPegawaiByNimSuspend(project.nim)

                            requestornot = viewmodel.checkRequestProject(project.id!!, user.nim).await()
                        }

                        KartuKonten(
                            fotoprofil = R.drawable.photo,
                            nama = pegawai.nama,
                            jurusan = pegawai.jurusan,
                            hari = formatRelativeTime(project.date!!),
                            type = "accept",
                            judul = project.title,
                            gambar = project.image ?: "",
                            konten = project.desc,
                            requests = project.requests!!,
                            tag = project.tag,

                            onclick = {

                            },
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    items(
                        items = user.requests!!
                    ){ projectId ->
                        Log.d("tes3", "ererer")
                        projectviewmodel.getProjectById(projectId)
                        val project = projectviewmodel.projectMap[projectId] ?: Project()
                        val pegawai = viewmodel.pegawaiMap[project.nim] ?: Pegawai()
                        var requestornot by remember { mutableStateOf(false) }

                        LaunchedEffect(projectId, project.nim) {
                            viewmodel.getPegawaiByNim(project.nim)
                            projectviewmodel.getProjectById(projectId)
                            requestornot = viewmodel.checkRequestProject(project.id!!, user.nim).await()
                        }

                        KartuKonten(
                            fotoprofil = R.drawable.photo,
                            nama = pegawai.nama,
                            jurusan = pegawai.jurusan,
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





