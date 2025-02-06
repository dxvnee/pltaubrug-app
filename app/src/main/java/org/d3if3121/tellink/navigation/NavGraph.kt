package org.d3if3121.tellink.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import org.d3if3121.tellink.ui.screen.ConfirmPage
import org.d3if3121.tellink.ui.screen.EditPage
import org.d3if3121.tellink.ui.screen.HomePage
import org.d3if3121.tellink.ui.screen.LoginPage
import org.d3if3121.tellink.ui.screen.ProfilePage
import org.d3if3121.tellink.ui.screen.ProjectPage
import org.d3if3121.tellink.ui.screen.RegisterPage
import org.d3if3121.tellink.ui.viewmodel.MahasiswaListViewModel
import org.d3if3121.tellink.ui.viewmodel.ProjectListViewModel


@Composable
fun SetupNavGraph(){

    val navController = rememberNavController()
    val mahasiswalistviewmodel: MahasiswaListViewModel = hiltViewModel()
    val projectlistviewmodel: ProjectListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route){
            LoginPage(navController, mahasiswalistviewmodel)
        }

        composable(route = Screen.Register.route){
            RegisterPage(navController, mahasiswalistviewmodel)
        }

        composable(route = Screen.Home.route){
            HomePage(navController, mahasiswalistviewmodel)
        }

        composable(route = Screen.Project.route){
            ProjectPage(navController, mahasiswalistviewmodel, projectlistviewmodel)
        }

        composable(route = Screen.Profile.route){
            ProfilePage(navController, mahasiswalistviewmodel)
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