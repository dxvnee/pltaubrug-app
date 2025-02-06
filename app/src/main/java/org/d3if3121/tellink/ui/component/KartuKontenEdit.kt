package org.d3if3121.tellink.ui.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.d3if3121.tellink.R
import org.d3if3121.tellink.components.LoadingIndicator
import org.d3if3121.tellink.core.printError
import org.d3if3121.tellink.data.model.ImageUpload
import org.d3if3121.tellink.data.model.Project
import org.d3if3121.tellink.data.model.Response.Failure
import org.d3if3121.tellink.data.model.Response.Loading
import org.d3if3121.tellink.data.model.Response.Success
import org.d3if3121.tellink.navigation.Screen
import org.d3if3121.tellink.ui.screen.MainContentProject
import org.d3if3121.tellink.ui.theme.Warna
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.ui.viewmodel.ProjectListViewModel


@Composable
fun KartuKontenEdit(
    navController: NavController,
    projectId: String,
    viewmodel: MahasiswaListViewModel = hiltViewModel(),
    projectviewmodel: ProjectListViewModel = hiltViewModel()
){
    Log.d("idproject", projectId)
    projectviewmodel.getProjectById(projectId)
    var project = projectviewmodel.project

    var judul by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf(listOf<String>()) }

    val context = LocalContext.current

    when(val updateProjectResponse = projectviewmodel.updateProjectResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Edit Success!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Project.route)
        }
        is Failure -> printError(updateProjectResponse.e)
    }

    when(val deleteProjectResponse = projectviewmodel.deleteProjectResponse){
        is Loading -> {

        }
        is Success -> {
            Toast.makeText(context, "Delete Success!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Project.route)
        }
        is Failure -> printError(deleteProjectResponse.e)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 17.dp, end = 17.dp, bottom = 20.dp)
            .height(600.dp),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(15.dp)
    ){

        LazyColumn(
            modifier = Modifier
                .padding(start = 17.dp, top = 23.dp, end = 17.dp, bottom = 23.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            item {
                Text(
                    text = project.title,
                    color = Warna.MerahNormal,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                AsyncImage(
                    model = project.image,
                    contentDescription = "Project Image",
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )

                InputPutih(
                    input = judul,
                    placeholder = stringResource(id = R.string.edit_title),
                    onInputChange = { input ->
                        judul = input
                    },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                )


                Text(
                    text = stringResource(id = R.string.desc),
                    color = Warna.MerahNormal,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 8.dp)
                )
                Text(
                    text = project.desc,
                    color = Warna.HitamNormal,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                InputPutih(
                    input = desc,
                    placeholder = stringResource(id = R.string.edit_desc),
                    onInputChange = { input ->
                        desc = input
                    },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(id = R.string.tags),
                    color = Warna.MerahNormal,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 8.dp)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        ButtonMerah(
                            onClick = {
                                var projects = Project(
                                    nim = viewmodel.user.nim,
                                    title = if(judul == "") project.title else judul,
                                    desc = if(desc == "") project.desc else desc,
                                    tag = if(selectedTag.isEmpty()) project.tag else selectedTag,
                                )
                                projectviewmodel.updateProject(projectId, projects)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 10.dp, end = 10.dp)
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
                        ButtonMerah(
                            onClick = {
                                projectviewmodel.deleteProject(projectId)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 10.dp)
                                .size(46.dp),
                            content = {
                                Text(
                                    text = "DELETE",
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
