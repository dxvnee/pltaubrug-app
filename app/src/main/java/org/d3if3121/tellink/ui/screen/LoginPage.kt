package org.d3if3121.tellink.ui.screen

import android.util.Log
import android.widget.Toast
import org.d3if3121.tellink.R

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.di.MAHASISWA
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.component.ButtonMerah
import org.d3if3121.tellink.ui.component.InputPassword
import org.d3if3121.tellink.ui.component.InputPutih
import org.d3if3121.tellink.ui.theme.Warna
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel


@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage(navController = rememberNavController())
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController: NavHostController,
    viewmodel: MahasiswaListViewModel = hiltViewModel()
) {
    var nim by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    var mahasiswa by remember { mutableStateOf(MahasiswaLogin(nim = "", password = " ")) }
    var errorMessage by remember { mutableStateOf("") }

    var context = LocalContext.current



    fun handleLogin() {
        if(nim.isNotEmpty() && password.isNotEmpty()){
            mahasiswa = MahasiswaLogin(
                nim = nim,
                password = password
            )

            viewmodel.loginMahasiswa(mahasiswa)

        } else {
            errorMessage = "All fields shouldn't be empty."
        }
    }

    when(val response = viewmodel.loginResponse){
        is Response.Success -> {
            viewmodel.addUser(response.data!!)
            Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Home.route)
        }
        is Response.Failure -> {
            errorMessage = response.e!!.message.toString()
        }
        is Response.Loading -> {
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Warna.PutihNormal, RectangleShape)
    ){

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 95.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .shadow(elevation = 25.dp, shape = CircleShape, ambientColor = Color.Red)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(
                            text = "Log in to ",
                            color = Warna.MerahNormal,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Normal,
                        )
                        Text(
                            text = "Tellink.",
                            color = Warna.MerahNormal,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Canvas(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 525.dp)
                .size(400.dp)

        ) {

            drawCircle(
                color = Warna.MerahTua,
                radius = size.minDimension
            )


        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 280.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .height(350.dp),

            colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
            elevation = CardDefaults.cardElevation(20.dp),
            shape = RoundedCornerShape(15.dp)
        )
        {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, top = 23.dp, end = 24.dp, bottom = 23.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
                ){
                    Column {
                        Text(
                            text = stringResource(id = R.string.nim),
                            color = Warna.MerahNormal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        InputPutih(
                            input = nim,
                            placeholder = stringResource(id = R.string.enter_nim),
                            onInputChange = { input ->
                                nim = input
                            },
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.fillMaxWidth()

                        )

                    }
                }

                Row(
                    modifier = Modifier.padding(top = 6.dp)
                ){
                    Column {
                        Text(
                            text = stringResource(id = R.string.password),
                            color = Warna.MerahNormal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        InputPassword(
                            input = password,
                            placeholder = stringResource(id = R.string.enter_password),
                            onInputChange = { input ->
                                password = input
                            },
                            keyboardType = KeyboardType.Password,
                            passwordVisible = passwordVisible,
                            modifiers = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = errorMessage,
                            color = Warna.MerahNormal,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )

                    }
                }


                Row {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        ButtonMerah(
                            onClick = {
                                handleLogin()

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                                .size(46.dp),
                            content = {
                                Text(
                                    text = "LOGIN",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Warna.PutihNormal
                                )
                            }
                        )


                        Row(

                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Don't have account? ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight(500)
                            )
                            ClickableText(
                                text = AnnotatedString("Register!"),
                                onClick = {
                                    navController.navigate(Screen.Register.route)
                                },
                                style = TextStyle.Default.copy(
                                    Warna.MerahNormal,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(500)
                                )
                            )
                        }

                    }
                }



            }
        }
    }

}