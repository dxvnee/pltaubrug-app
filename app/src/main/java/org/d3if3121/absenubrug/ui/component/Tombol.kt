package org.d3if3121.absenubrug.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.d3if3121.absenubrug.ui.theme.Warna


@Composable
fun ButtonMerah(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor =  Warna.MerahNormal,
        contentColor =  Warna.PutihNormal
    )
){
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        content = {
            content()
        },
        colors = colors
    )
}


@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(icon, contentDescription = "Edit", tint = Color.White, modifier = Modifier.padding(end =5.dp))
        Text(text, color = Color.White)
    }
}