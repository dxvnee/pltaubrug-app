package org.d3if3121.pltaconnect.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector,
){
    object BottomMenuPage : BottomBarScreen(
        route = "HomePage",
        title = "Home",
        icon = Icons.Default.Home
    )
    object BottomProfilePage : BottomBarScreen(
        route = "ProfilePage",
        title = "Profile",
        icon = Icons.Default.Person
    )
    object BottomSkillPage : BottomBarScreen(
        route = "ProjectPage",
        title = "Project",
        icon = Icons.Default.Star
    )
}