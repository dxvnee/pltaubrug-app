package org.d3if3121.AdminAbsen.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerExample() {
    val openDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Menyimpan tanggal yang dipilih dalam format yang mudah dibaca
    val selectedDate = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { openDialog.value = true }) {
            Text(text = "Pilih Tanggal")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Tanggal: ${if (selectedDate.value.isNotEmpty()) selectedDate.value else "Belum dipilih"}")

        if (openDialog.value) {
            DatePickerDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        openDialog.value = false

                        // Ambil tanggal dari DatePickerState
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = selectedMillis

                            // Format tanggal ke "dd/MM/yyyy"
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            selectedDate.value = dateFormat.format(calendar.time)
                        }
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
