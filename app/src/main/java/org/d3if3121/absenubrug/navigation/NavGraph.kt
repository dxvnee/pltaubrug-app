package org.d3if3121.absenubrug.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3121.absenubrug.ui.screen.HomePage
import org.d3if3121.absenubrug.ui.screen.LoginPage
import org.d3if3121.absenubrug.ui.screen.ProfilePage
import org.d3if3121.absenubrug.ui.screen.ProjectPage
import org.d3if3121.absenubrug.ui.screen.RegisterPage
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel


@Composable
fun SetupNavGraph(isLoggedIn: Boolean){

    val navController = rememberNavController()
    val mahasiswalistviewmodel: MahasiswaListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination =  if (isLoggedIn) Screen.Home.route else Screen.Login.route
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

        composable(route = Screen.Profile.route){
            ProfilePage(navController, mahasiswalistviewmodel)
        }

        composable(route ="${Screen.Project.route}/{tanggal}",
            arguments = listOf(navArgument("tanggal"){
                type = NavType.StringType
                nullable = false
            })
        ){ backStackEntry ->
            val tanggal = backStackEntry.arguments?.getString("tanggal")
            ProjectPage(navController, mahasiswalistviewmodel, tanggal = tanggal!!)
        }

        composable(route = Screen.Profile.route){
            ProfilePage(navController, mahasiswalistviewmodel)
        }

    }
}