package org.d3if3121.tellink.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.d3if3121.tellink.navigation.BottomBarScreen
import org.d3if3121.tellink.ui.theme.Warna


@Composable
fun BottomBar(navController: NavHostController, home: Boolean = false, homeAction: () -> Unit = {}){
    val screens = listOf(
        BottomBarScreen.BottomMenuPage,
        BottomBarScreen.BottomSkillPage,
        BottomBarScreen.BottomProfilePage,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Warna.MerahTua,
        modifier = Modifier.height(70.dp)
    ){
        screens.forEach{ screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController, home, homeAction)
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    home: Boolean = false,
    homeAction: () -> Unit
){
    BottomNavigationItem (
        modifier = Modifier.padding(top = 10.dp, bottom = 36.dp),
        label = {
            Text(
                text = screen.title, color = Warna.PutihNormal,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        icon = {
            Icon(
                modifier = Modifier.size(65.dp)
                    .padding(bottom =1.dp),
                imageVector = screen.icon,
                contentDescription = "eheh",
                tint = Warna.PutihNormal
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }

            if (home) {
                homeAction()
            }
        }

    )


}