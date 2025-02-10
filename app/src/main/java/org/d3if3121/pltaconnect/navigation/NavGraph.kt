package org.d3if3121.pltaconnect.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3121.pltaconnect.ui.screen.ConfirmPage
import org.d3if3121.pltaconnect.ui.screen.DebitSungaiPage
import org.d3if3121.pltaconnect.ui.screen.EditPage
import org.d3if3121.pltaconnect.ui.screen.HomePage
import org.d3if3121.pltaconnect.ui.screen.LoginPage
import org.d3if3121.pltaconnect.ui.screen.ProfilePage
import org.d3if3121.pltaconnect.ui.screen.ProjectPage
import org.d3if3121.pltaconnect.ui.screen.RegisterPage
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import org.d3if3121.pltaconnect.ui.viewmodel.ProjectListViewModel


@Composable
fun SetupNavGraph(){

    val navController = rememberNavController()
    val pegawailistviewmodel: PegawaiListViewModel = hiltViewModel()
    val projectlistviewmodel: ProjectListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route){
            LoginPage(navController, pegawailistviewmodel)
        }

        composable(route = Screen.Register.route){
            RegisterPage(navController, pegawailistviewmodel)
        }

        composable(route = Screen.Home.route){
            HomePage(navController, pegawailistviewmodel)
        }

        composable(route = Screen.Project.route){
            DebitSungaiPage(navController, pegawailistviewmodel)
        }

        composable(route = Screen.Profile.route){
            ProfilePage(navController, pegawailistviewmodel)
        }

        composable(route ="${Screen.EditProject.route}/{projectId}",
            arguments = listOf(navArgument("projectId"){
                type = NavType.StringType
                nullable = false
            })
        ){ backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")
            EditPage(navController = navController, projectId = projectId)
        }

        composable(route ="${Screen.ConfirmPage.route}/{projectId}",
            arguments = listOf(navArgument("projectId"){
                type = NavType.StringType
                nullable = false
            })
        ){ backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")
            ConfirmPage(navController = navController, projectId = projectId)
        }
    }
}