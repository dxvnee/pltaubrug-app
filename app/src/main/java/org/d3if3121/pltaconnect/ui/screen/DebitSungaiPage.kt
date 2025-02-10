package org.d3if3121.pltaconnect.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.ui.theme.Warna
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.pltaconnect.R
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
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.Calendar
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.InputPutih
import org.d3if3121.pltaconnect.ui.component.InputPutihKeterangan
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.screen.content.ContentDebit
import org.d3if3121.pltaconnect.ui.screen.content.ContentProduksi
import org.d3if3121.pltaconnect.ui.screen.content.DebitSungaiResponse


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContentPage(
    navController: NavHostController,
    viewModel: PegawaiListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
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
                                context = context
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
fun MainContent(
    navController: NavHostController,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    viewmodel: PegawaiListViewModel,
    projectviewmodel: ProjectListViewModel,
    projectList: List<Project>,
    viewModel: PegawaiListViewModel = hiltViewModel(),
    user: Pegawai,
    context: Context = LocalContext.current
) {

    var contentnow by  remember { mutableStateOf("Debit") }
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
                    lazyListState = lazyListState,
                    onClickNext = {
                        contentnow = "Produksi"
                    },
                    onClickBack = {
                        contentnow = "Debit"
                    }
                )
                DebitSungaiResponse(context, projectviewmodel)
            }
            "Produksi" -> {
                ContentProduksi(
                    lazyListState = lazyListState,
                    onClickNext = {
                        contentnow = "Debit"
                    },
                    onClickBack = {
                        contentnow = "Produksi"
                    }
                )
            }
        }

    }
}



