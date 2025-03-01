package org.d3if3121.absenubrugadmin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.d3if3121.absenubrugadmin.R

@Composable
fun FotoProfil(
    modifier: Modifier = Modifier,
    imageUrl: String? = ""
){
    if(imageUrl == ""){
        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = "Foto Profil",
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Foto Profil",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
        )
    }

}