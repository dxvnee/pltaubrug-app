package org.d3if3121.absenubrug.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.produceState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import org.d3if3121.absenubrug.data.datastore.UserPreferences
import org.d3if3121.absenubrug.navigation.SetupNavGraph
import org.d3if3121.absenubrug.ui.theme.TellinkTheme
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: MahasiswaListViewModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences

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
                    Log.d("geg", isLoggedIn.toString())
                    SetupNavGraph(isLoggedIn)
                }
            }
        }
    }
}
