package org.d3if3121.pltaconnect.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.d3if3121.pltaconnect.navigation.SetupNavGraph
import org.d3if3121.pltaconnect.ui.theme.TellinkTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TellinkTheme {
                SetupNavGraph()
//                EditPage(rememberNavController())
            }
        }
    }
}
