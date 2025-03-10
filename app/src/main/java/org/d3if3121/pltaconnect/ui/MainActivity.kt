package org.d3if3121.pltaconnect.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if3121.pltaconnect.data.model.PegawaiLogin
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.navigation.SetupNavGraph
import org.d3if3121.pltaconnect.ui.theme.TellinkTheme
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: PegawaiListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val userId: String? = runBlocking { viewmodel.getUserId() }
        val password: String? = runBlocking { viewmodel.getPassword() }

//        if(userId != null){
//            viewmodel.loginPegawai()
//            viewmodel
//        }
        Log.d("warw2", userId.toString())

        setContent {
            TellinkTheme {
                SetupNavGraph()
            }
        }

    }
}
