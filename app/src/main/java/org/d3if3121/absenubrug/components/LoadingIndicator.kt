package org.d3if3121.absenubrug.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.d3if3121.absenubrug.ui.theme.Warna

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier= Modifier.fillMaxSize(),
        color = Warna.MerahTua
    )

}