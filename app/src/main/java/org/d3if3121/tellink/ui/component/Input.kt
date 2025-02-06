package org.d3if3121.tellink.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import org.d3if3121.tellink.R
import org.d3if3121.tellink.ui.theme.Warna

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPutih(
    input: String,
    placeholder: String,
    onInputChange: (String) -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    expand: Boolean = false,
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Warna.AbuTua,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(
                max = if(expand) Int.MAX_VALUE.dp else 50.dp,
                min = if(expand) 180.dp else 50.dp
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Warna.HitamNormal,
            unfocusedTextColor = Warna.HitamNormal,
            focusedPlaceholderColor = Warna.MerahNormal,
            focusedIndicatorColor = Warna.MerahNormal,
            unfocusedIndicatorColor = Warna.AbuMuda,
            focusedContainerColor = if (isFocused) Warna.PutihNormal else Warna.AbuMuda,
            unfocusedContainerColor = if (isFocused) Warna.PutihNormal else Warna.AbuMuda
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        maxLines = if(expand) Int.MAX_VALUE else 1,
        minLines = 1,
    )
}


@Composable
fun InputPutihSearchProfile(
    input: String,
    placeholder: String,
    onInputChange: (String) -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    fontSize: Int = 17
){
    var isFocused by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .border(1.dp, if (!isFocused) Warna.AbuMuda else Warna.MerahNormal, RoundedCornerShape(10.dp)).fillMaxHeight(),
        colors = if (!isFocused) CardDefaults.cardColors(Warna.AbuMuda) else CardDefaults.cardColors(
            Warna.PutihNormal)

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight().padding(start = 10.dp)
        ){
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "eheh",
                tint = Warna.AbuTua
            )
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxHeight().padding(start = 7.dp)
            ){
                if (input.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Warna.AbuTua,
                        fontSize = fontSize.sp,
                        style = TextStyle(
                            fontSize = fontSize.sp,
                            color = Warna.HitamNormal,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.offset(y = 8.dp)

                    )
                }
                BasicTextField(
                    value =  input,
                    onValueChange = onInputChange,
                    textStyle = TextStyle(
                        fontSize = fontSize.sp,
                        color = Warna.HitamNormal,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = if (input.isEmpty()) -9.dp else 0.dp)
                        .onFocusChanged {
                            isFocused = it.isFocused
                        },
                )
            }
        }


    }
}
@Composable
fun InputPutihSearch(
    input: String,
    placeholder: String,
    onInputChange: (String) -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    fontSize: Int = 17,
){
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        leadingIcon = {
            Icon(
                modifier = iconModifier.size(25.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "eheh",
                tint = Warna.AbuTua,

            )
        },
        textStyle = TextStyle(
            color = Warna.HitamNormal,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Normal,
        ),
        value = input,
        onValueChange = onInputChange,
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                color = Warna.AbuTua,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(48.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Warna.HitamNormal,
            unfocusedTextColor = Warna.HitamNormal,
            focusedPlaceholderColor = Warna.MerahNormal,
            focusedIndicatorColor = Warna.MerahNormal,
            unfocusedIndicatorColor = Warna.AbuMuda,
            focusedContainerColor = if (isFocused) Warna.PutihNormal else Warna.AbuMuda,
            unfocusedContainerColor = if (isFocused) Warna.PutihNormal else Warna.AbuMuda
        ),


        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPassword(
    input: String,
    placeholder: String,
    onInputChange: (String) -> Unit,
    keyboardType: KeyboardType,
    passwordVisible: MutableState<Boolean>,
    modifiers: Modifier = Modifier
){
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        placeholder = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = placeholder,
                    color = Warna.AbuTua,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
            }

        },

        shape = RoundedCornerShape(10.dp),
        modifier = modifiers
            .height(50.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Warna.HitamNormal,
            unfocusedTextColor = Warna.HitamNormal,
            focusedPlaceholderColor = Warna.MerahNormal,
            focusedIndicatorColor = Warna.MerahNormal,
            unfocusedIndicatorColor = Warna.AbuMuda,
            focusedContainerColor = if (isFocused) Warna.PutihNormal else Warna.AbuMuda,
            unfocusedContainerColor = if (isFocused) Warna.PutihNormal else Warna.AbuMuda
        ),

        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image =
                if (passwordVisible.value) painterResource(id = R.drawable.baseline_visibility_24)
                else painterResource(id = R.drawable.baseline_visibility_off_24)

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(
                    painter = image,
                    contentDescription = if (passwordVisible.value) "Hide password" else "Show password",
                    tint = Warna.MerahNormal
                )
            }
        }

    )
}


