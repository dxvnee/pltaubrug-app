package org.d3if3121.absenubrug.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.d3if3121.absenubrug.ui.theme.Warna

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier= Modifier.fillMaxSize(),
        color = Warna.MerahTua
    )

}