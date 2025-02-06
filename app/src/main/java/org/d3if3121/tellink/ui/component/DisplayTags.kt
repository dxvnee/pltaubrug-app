package org.d3if3121.tellink.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.tellink.ui.theme.Warna


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisplayTag(
    selectedTag: List<String> = emptyList(),
    onTagRemove: (String) -> Unit = {}
){
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 5,
        overflow = FlowRowOverflow.Clip
    ){
        selectedTag.forEach { tag ->
            Card(
                modifier = Modifier
                    .padding(end = 10.dp, bottom = 10.dp)
                    .wrapContentWidth()
                    .height(30.dp)
                    .clickable {
                        onTagRemove(tag)
                    }
                ,

                colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, Warna.MerahNormal)
            ){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Text(
                        text = tag,
                        color = Warna.MerahNormal,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }

    }
}
