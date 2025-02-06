package org.d3if3121.tellink.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.d3if3121.tellink.R
import org.d3if3121.tellink.data.model.Tag

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownTag(
    selectedTag: String,
    onTagSelected: (String) -> Unit
) {
    var searchTags by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf("") }

    val items = Tag()
    val filteredTag = items.getFilteredTags(searchTags)


    Column(modifier = Modifier.fillMaxWidth()) {
        InputPutihSearch(
            input = searchTags,
            placeholder = stringResource(id = R.string.search_tag),
            onInputChange = { input ->
                searchTags = input
            },
            keyboardType = KeyboardType.Text,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    expanded = focusState.isFocused
                },
            iconModifier = Modifier.clickable {
                expanded = true
            }

        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteredTag.forEach { tag ->
                    DropdownMenuItem(
                        onClick = {
                            onTagSelected(tag)
                            searchTags = ""
                            expanded = false
                        }
                    ) {
                        Text(text = tag)
                    }
                }
            }
        }
    }
}
