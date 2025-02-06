package org.d3if3121.tellink.ui.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import org.d3if3121.tellink.R
import org.d3if3121.tellink.data.model.MahasiswaEdit
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.theme.Warna
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.data.model.Response.Loading
import org.d3if3121.tellink.data.model.Response.Success
import org.d3if3121.tellink.data.model.Response.Failure

@Composable
fun EditMahasiswaDialog(
    showDialog: MutableState<Boolean>,
    viewmodel: MahasiswaListViewModel = hiltViewModel()
){
    val user = viewmodel.user
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var major by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    fun handleEditMahasiswa(){
        if (name != "" && major != ""){
            val mahasiswa = MahasiswaEdit(
                nim = user.nim,
                nama = name,
                major = major
            )
            viewmodel.updateMahasiswa(mahasiswa)
        } else {
            errorMessage = "All fields shouldn't be empty."
        }
    }
    when(val response = viewmodel.updateMahasiswaResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Edit Success!", Toast.LENGTH_SHORT).show()
            viewmodel.editUser(name, major)
            showDialog.value = false
        }
        is Failure -> {
            errorMessage = response.e.toString()
        }
    }



    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
            }
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
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

                    Text(
                        text = stringResource(id = R.string.full_name),
                        color = Warna.MerahNormal,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    InputPutih(
                        input = name,
                        placeholder = stringResource(id = R.string.enter_name),
                        onInputChange = { input ->
                            name = input
                        },
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = stringResource(id = R.string.major),
                        color = Warna.MerahNormal,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    )

                    InputPutih(
                        input = major,
                        placeholder = stringResource(id = R.string.major),
                        onInputChange = { input ->
                            major = input
                        },
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = errorMessage,
                            color = Warna.MerahNormal,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        ButtonMerah(
                            onClick = {
                                handleEditMahasiswa()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                                .size(46.dp),
                            content = {
                                Text(
                                    text = "EDIT",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Warna.PutihNormal
                                )
                            }
                        )

                    }
                }


            }
        }
    }
}


