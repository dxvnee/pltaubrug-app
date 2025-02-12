package org.d3if3121.pltaconnect.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3121.pltaconnect.ui.screen.ContentPage
import org.d3if3121.pltaconnect.ui.screen.HomePage
import org.d3if3121.pltaconnect.ui.screen.LoginPage
import org.d3if3121.pltaconnect.ui.screen.ProfilePage
import org.d3if3121.pltaconnect.ui.screen.RegisterPage
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel


@Composable
fun SetupNavGraph(){

    val navController = rememberNavController()
    val pegawailistviewmodel: PegawaiListViewModel = hiltViewModel()

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

        composable(route ="${Screen.Project.route}/{tanggal}",
            arguments = listOf(navArgument("tanggal"){
                type = NavType.StringType
                nullable = false
            })
        ){ backStackEntry ->
            val tanggal = backStackEntry.arguments?.getString("tanggal")
            ContentPage(navController, pegawailistviewmodel, tanggal = tanggal)
        }

        composable(route = Screen.Profile.route){
            ProfilePage(navController, pegawailistviewmodel)
        }


    }
}