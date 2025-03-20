package org.d3if3121.pltaconnect.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if3121.pltaconnect.data.datastore.UserPreferences
import org.d3if3121.pltaconnect.data.model.PegawaiLogin
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.navigation.SetupNavGraph
import org.d3if3121.pltaconnect.ui.theme.TellinkTheme
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: PegawaiListViewModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences  // Inject UserPreferences dengan Hilt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var isLoading = true
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { isLoading }

        setContent {
            TellinkTheme {
                val isLoggedIn = produceState<Boolean?>(initialValue = null) {
                    value = userPreferences.isloggedin.first()
                    isLoading = false
                }.value

                if (isLoggedIn != null) {
                    SetupNavGraph(isLoggedIn)
                }
            }
        }
    }
}

