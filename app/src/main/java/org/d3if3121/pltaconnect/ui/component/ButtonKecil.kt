package org.d3if3121.pltaconnect.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.d3if3121.pltaconnect.ui.theme.Warna

@Composable
fun ButtonKecil(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box (
        modifier = Modifier.size(25.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Warna.MerahNormal)
            .clickable { onClick() }
    ){
        Column (
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                imageVector = imageVector,
                contentDescription = "Back",
                modifier = modifier.size(11.dp)
            )
        }

    }
}