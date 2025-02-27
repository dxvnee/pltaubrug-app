package org.d3if3121.absenubrugadmin.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.d3if3121.absenubrugadmin.ui.theme.Warna



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
