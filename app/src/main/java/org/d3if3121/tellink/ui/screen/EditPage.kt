package org.d3if3121.tellink.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3121.tellink.R
import org.d3if3121.tellink.ui.component.BottomBar
import org.d3if3121.tellink.ui.component.InputPutihSearchProfile
import org.d3if3121.tellink.ui.component.KartuKontenEdit
import org.d3if3121.tellink.ui.theme.Warna


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPage(
    navController: NavHostController,
    projectId: String? = "996GZuXUa03N1JRmSkyM",
){

    Scaffold(
        topBar = {
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
                                onClick = {
                                    navController.popBackStack()
                                },
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
                            Text(
                                text = "Edit Project",
                                color = Warna.MerahNormal,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold,

                                )

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
        },

        bottomBar = {
            BottomBar(navController = navController)
        }
    ){ paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues)
                .background(color = Warna.PutihNormal)
                .fillMaxSize()
        ){
            KartuKontenEdit(navController, projectId!!)
        }
    }
}


