package org.d3if3121.tellink.ui.component

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import org.d3if3121.tellink.R
import org.d3if3121.tellink.data.model.ImageUpload
import org.d3if3121.tellink.data.model.MahasiswaEdit
import org.d3if3121.tellink.data.model.MahasiswaLogin
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.theme.Warna
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.data.model.Response.Loading
import org.d3if3121.tellink.data.model.Response.Success
import org.d3if3121.tellink.data.model.Response.Failure
import org.d3if3121.tellink.ui.viewmodel.ProjectListViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.concurrent.timerTask

@Composable
fun TambahProjectDialog(
    showDialog: MutableState<Boolean>,
    refreshData: MutableState<Boolean>,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
){
    val user = viewmodel.user
    val context = LocalContext.current

    var showProgressDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    var judul by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var selectedTag by remember { mutableStateOf(listOf<String>()) }

    fun handlePost(){
        if (judul != "" && desc != "" && selectedTag.size == 3){
            var project: Project
            val currentDateTime = LocalDateTime.now()
            val isoDate = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME)

            if(imageUri != null){
                imageUri.let { uri ->
                    project = Project(
                        nim = user.nim,
                        title = judul,
                        desc = desc,
                        imageupload = ImageUpload(
                            uri = uri!!,
                        ),
                        tag = selectedTag,
                        date = isoDate
                    )
                }
            } else {
                project = Project(
                    nim = user.nim,
                    title = judul,
                    desc = desc,
                    tag = selectedTag,
                    date = isoDate
                )
            }
            projectviewmodel.addProject(project)
        } else {
            errorMessage = "All fields shouldn't be empty."
        }

    }

    fun resetAddImage(){
        projectviewmodel.resetAddProjectResponse()
        imageUri = null
        judul = ""
        desc =  ""
    }

    when(val response = projectviewmodel.addProjectResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Post Success!", Toast.LENGTH_SHORT).show()
            showDialog.value = false
            showProgressDialog = false
            resetAddImage()
        }
        is Failure -> {
            errorMessage = response.e.toString()
        }
    }

    if (showDialog.value) {


        Dialog(
            onDismissRequest = {
                showDialog.value = false
                refreshData.value = true
            },
        ){

            Card(
                modifier = Modifier
                    .width(600.dp)
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .height(900.dp),
                colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
                elevation = CardDefaults.cardElevation(20.dp),
                shape = RoundedCornerShape(15.dp)
            )
            {
                Text(
                    text = "Add Project Post",
                    color = Warna.MerahNormal,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(start = 24.dp, top = 23.dp, end = 24.dp, bottom = 13.dp)

                )

                LazyColumn(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 23.dp, end = 24.dp, bottom = 23.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.title),
                            color = Warna.MerahNormal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        InputPutih(
                            input = judul,
                            placeholder = stringResource(id = R.string.enter_title),
                            onInputChange = { input ->
                                judul = input
                            },
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(id = R.string.desc),
                            color = Warna.MerahNormal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp)
                        )

                        InputPutih(
                            input = desc,
                            placeholder = stringResource(id = R.string.enter_desc),
                            onInputChange = { input ->
                                desc = input
                            },
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth(),
                            expand = true
                        )

                        Text(
                            text = stringResource(id = R.string.upload_image),
                            color = Warna.MerahNormal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp)
                        )

                        val painter = if (imageUri != null) {
                            rememberAsyncImagePainter(imageUri)
                        } else {
                            painterResource(id = R.drawable.logo)
                        }

                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(250.dp)
                                .width(250.dp)
                                .border(
                                    width = 1.dp,
                                    color = Warna.HitamNormal,
                                    shape = RoundedCornerShape(8.dp)
                                ).clickable {
                                    launcher.launch("image/*")
                                }
                        )

                        Text(
                            text = stringResource(id = R.string.tags),
                            color = Warna.MerahNormal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp)
                        )


                        DisplayTag(
                            selectedTag = selectedTag,
                            onTagRemove = { tag ->
                                selectedTag = selectedTag - tag
                            }
                        )

                        DropdownTag(
                            selectedTag = "",
                            onTagSelected = { tag ->
                                if (selectedTag.size < 3){
                                    selectedTag = selectedTag + tag
                                }
                            }
                        )

                        if (showProgressDialog) {
                            CircularProgressIndicator(
                                color = Warna.PutihNormal,
                                strokeWidth = 4.dp,
                                modifier = Modifier.wrapContentSize(Alignment.Center)
                                    .size(50.dp)
                            )
                        }

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
                                    handlePost()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp)
                                    .size(46.dp),
                                content = {
                                    Text(
                                        text = "POST",
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
}


