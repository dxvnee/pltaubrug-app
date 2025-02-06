package org.d3if3121.tellink.ui.component

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.d3if3121.tellink.R
import org.d3if3121.tellink.data.model.Mahasiswa
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.screen.TOP_BAR_HEIGHT
import org.d3if3121.tellink.ui.theme.Warna
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    lazyListState: LazyListState,
    helloActive: Boolean,
    profileActive: Boolean = false,
    TOP_BAR_ZERO: Int = 0,
    search: String = "",
    onSearchChange: (String) -> Unit = {},
    navController: NavHostController = rememberNavController(),
    user: Mahasiswa? = null
){

    val currentUser = user ?: Mahasiswa()

    if (profileActive == true){

        TopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Column (
                        horizontalAlignment = Alignment.Start,
                    ){
                        IconButton(
                            onClick = {},
                            modifier = Modifier.offset(x = -12.dp)
                        ){
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "eheh",
                                tint = Warna.AbuTua
                            )
                        }

                    }
                    Column (

                    ){
//                        InputPutihSearchProfile(
//                            input = search,
//                            placeholder = stringResource(id = R.string.search),
//                            onInputChange = onSearchChange,
//                            keyboardType = KeyboardType.Text,
//                            modifier = Modifier.fillMaxWidth()
//                                .padding( end = 17.dp).height(40.dp),
//                            fontSize = 15
//
//                        )
                    }


                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Warna.PutihNormal,
                titleContentColor = Warna.PutihNormal
            ),
            modifier = Modifier
                .background(color = Warna.PutihNormal).padding(top = 0.dp)

        )
    } else {
        TopAppBar(
            title = {
                Column(
                    modifier = Modifier.padding(top = 7.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column (
                            modifier = Modifier.padding(end = 9.dp)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.photo),
                                contentDescription = "App logo",
                                modifier = Modifier
                                    .size(45.dp)
                            )
                        }

                        Column (

                        ){
                            if (helloActive == true) {
                                Text(
                                    text = "Hello,",
                                    color = Warna.MerahNormal,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.offset(y = 5.dp)
                            )
                                Text(
                                    text = currentUser.nama,
                                    color = Warna.HitamNormal,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.offset(y = -4.dp)
                                )
                            } else {
                                Text(
                                    text = currentUser.nama,
                                    color = Warna.HitamNormal,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.offset(y = 5.dp)
                                )
                                Text(
                                    text = currentUser.nim,
                                    color = Warna.AbuTua,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.offset(y = -4.dp)
                                )
                            }




                        }
                        Row (
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ){
//                            TombolGambar(
//                                painterResource(id = R.drawable.notifications),
//                                26
//                            ){
//
//                            }
//                            TombolGambar(
//                                painterResource(id = R.drawable.chat),
//                                26
//                            ){
//                                navController.navigate(Screen.Login.route)
//                            }
                        }

                    }


                }

            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Warna.PutihNormal,
                titleContentColor = Warna.PutihNormal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Warna.PutihNormal)
                .animateContentSize(animationSpec =  tween(
                    durationMillis = 500,
                )
                )
                .height(height =
                if (cekScroll(lazyListState)){
                    TOP_BAR_ZERO.dp
                    if (ScrollDirectionDetector(lazyListState) == "Up" && ObserveScrollState(lazyListState)){
                        Log.d("cekFlow", ObserveScrollState(lazyListState).toString())
                        TOP_BAR_HEIGHT
                    } else {
                        TOP_BAR_ZERO.dp
                    }
                } else {
                    TOP_BAR_HEIGHT
                }
                ),
        )
    }

}


@Composable
fun ObserveScrollState(lazyListState: LazyListState) : Boolean{
    var isScrolledFar by remember { mutableStateOf(false) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset > 28 }
            .distinctUntilChanged()
            .debounce(160)
            .collect { newIsScrolledFar ->
                isScrolledFar = newIsScrolledFar
            }
    }

    return isScrolledFar
}

@Composable
fun cekScroll(lazyListState: LazyListState) : Boolean{
    var isScrolled by remember { mutableStateOf(false) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex > 0 }
            .distinctUntilChanged()
            .debounce(80)
            .collect {
                isScrolled = it
            }
    }
    return isScrolled
}

@Composable
fun ScrollDirectionDetector(lazyListState: LazyListState): String {
    var previousIndex by remember { mutableStateOf(lazyListState.firstVisibleItemIndex) }
    var scrollDirection by remember { mutableStateOf("Idle") }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { currentIndex ->
                scrollDirection = when {
                    currentIndex > previousIndex -> "Down"
                    currentIndex < previousIndex -> "Up"
                    else -> "Idle"
                }
                previousIndex = currentIndex
            }
    }

    return scrollDirection
}

