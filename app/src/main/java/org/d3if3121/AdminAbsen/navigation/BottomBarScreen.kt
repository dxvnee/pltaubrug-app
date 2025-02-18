package org.d3if3121.AdminAbsen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermContactCalendar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector,
){
    object BottomMenuPage : BottomBarScreen(
         route = "HomePage",
        title = "Absen",
        icon = Icons.Default.PermContactCalendar
    )
    object BottomProfilePage : BottomBarScreen(
        route = "ProfilePage",
        title = "Edit",
        icon = Icons.Default.EditNote
    )
    object BottomSkillPage : BottomBarScreen(
        route = "ProjectPage",
        title = "Data",
        icon = Icons.Default.Dataset
    )
}