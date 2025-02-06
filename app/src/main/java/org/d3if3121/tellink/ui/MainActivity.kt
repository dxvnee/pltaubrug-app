package org.d3if3121.tellink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.d3if3121.tellink.navigation.SetupNavGraph
import org.d3if3121.tellink.ui.screen.EditPage
import org.d3if3121.tellink.ui.theme.TellinkTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TellinkTheme {
                SetupNavGraph()
//                EditPage(rememberNavController())
            }
        }
    }
}
