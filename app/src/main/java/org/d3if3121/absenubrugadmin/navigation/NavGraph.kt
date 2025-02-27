package org.d3if3121.absenubrugadmin.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3121.absenubrugadmin.ui.screen.EmployeePage
import org.d3if3121.absenubrugadmin.ui.screen.HomePage
import org.d3if3121.absenubrugadmin.ui.screen.LoginPage
import org.d3if3121.absenubrugadmin.ui.screen.ProfilePage
import org.d3if3121.absenubrugadmin.ui.screen.ProjectPage
import org.d3if3121.absenubrugadmin.ui.screen.RegisterPage
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel


@Composable
fun SetupNavGraph(){

    val navController = rememberNavController()
    val mahasiswalistviewmodel: MahasiswaListViewModel = hiltViewModel()

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

        composable(route = Screen.Profile.route){
            ProfilePage(navController, mahasiswalistviewmodel)
        }

        composable(route = Screen.Employee.route){
            EmployeePage(navController, mahasiswalistviewmodel)
        }

        composable(route = Screen.Project.route){
            ProjectPage(navController, mahasiswalistviewmodel)
        }

        composable(route = Screen.Profile.route){
            ProfilePage(navController, mahasiswalistviewmodel)
        }

    }
}