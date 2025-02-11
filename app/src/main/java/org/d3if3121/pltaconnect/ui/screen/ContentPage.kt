package org.d3if3121.pltaconnect.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.ui.theme.Warna
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.pltaconnect.components.LoadingIndicator
import org.d3if3121.pltaconnect.core.printError
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.Project
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.ui.component.BottomBar
import org.d3if3121.pltaconnect.ui.component.TopBar
import org.d3if3121.pltaconnect.ui.component.cekScroll
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import org.d3if3121.pltaconnect.ui.viewmodel.ProjectListViewModel
import org.d3if3121.pltaconnect.ui.screen.content.ContentDebit
import org.d3if3121.pltaconnect.ui.screen.content.ContentMasuk
import org.d3if3121.pltaconnect.ui.screen.content.ContentPemakaian
import org.d3if3121.pltaconnect.ui.screen.content.ContentProduksi
import org.d3if3121.pltaconnect.ui.screen.content.DebitSungaiResponse


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContentPage(
    navController: NavHostController,
    viewModel: PegawaiListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel(),
    tanggal: String?
) {
    val lazyListState = rememberLazyListState()
    var user = viewModel.user
    var context = LocalContext.current

    LaunchedEffect(user.nim) {
        projectviewmodel.getProjectListUser(user.nim)
    }

    Scaffold(
        topBar = {
            TopBar(lazyListState = lazyListState, helloActive = false, TOP_BAR_ZERO = 70, user = user)
        },
        content = { paddingValues ->
            when(val projectListUserResponse = projectviewmodel.projectListUserResponse){
                is Loading -> LoadingIndicator()
                is Success -> projectListUserResponse.data.let { projectList ->
                        Column(modifier = Modifier.background(color = Warna.PutihNormal)){
                            MainContent(
                                navController = navController,
                                lazyListState = lazyListState,
                                paddingValues = paddingValues,
                                viewmodel = viewModel,
                                projectviewmodel = projectviewmodel,
                                projectList = projectList!!,
                                user = user,
                                context = context,
                                tanggal = tanggal ?: "5 Juli 2004"
                            )
                        }

                }
                is Failure -> printError(projectListUserResponse.e)
            }

        },
        bottomBar = {
            BottomBar(navController = navController, home = true, pegawaiListViewModel = viewModel)
        },
        contentColor = Warna.PutihNormal
    )

}

@Composable
fun MainContent(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: PegawaiListViewModel,
    projectviewmodel: ProjectListViewModel,
    projectList: List<Project>,
    viewModel: PegawaiListViewModel = hiltViewModel(),
    user: Pegawai,
    context: Context = LocalContext.current,
    tanggal: String
) {

    var contentnow by  remember { mutableStateOf("Pemakaian") }
    val padding by animateDpAsState(
        targetValue = if (cekScroll(lazyListState)) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(
            durationMillis = 500,
            )
    )

    Column(
        modifier = Modifier.padding(start = 17.dp, end = 17.dp, top = padding)
    ) {
        when(contentnow){
            "Debit" -> {
                ContentDebit(
                    tanggal = tanggal,
                    lazyListState = lazyListState,
                    onClickNext = {
                        contentnow = "Produksi"
                    },
                    onClickBack = {
                        contentnow = "Masuk"
                    }
                )
                DebitSungaiResponse(context, projectviewmodel)
            }
            "Produksi" -> {
                ContentProduksi(
                    tanggal = tanggal,
                    lazyListState = lazyListState,
                    onClickNext = {
                        contentnow = "Pemakaian"
                    },
                    onClickBack = {
                        contentnow = "Debit"
                    }
                )
            }
            "Pemakaian" -> {
                ContentPemakaian(
                    tanggal = tanggal,
                    lazyListState = lazyListState,
                    onClickNext = {
                        contentnow = "Masuk"
                    },
                    onClickBack = {
                        contentnow = "Produksi"
                    }
                )
            }
            "Masuk" -> {
                ContentMasuk(
                    tanggal = tanggal,
                    lazyListState = lazyListState,
                    onClickNext = {
                        contentnow = "Debit"
                    },
                    onClickBack = {
                        contentnow = "Pemakaian"
                    }
                )
            }
        }

    }
}



