package org.d3if3121.absenubrugadmin.ui.component

import org.d3if3121.absenubrugadmin.ui.theme.Warna


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrugadmin.ui.viewmodel.MahasiswaListViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    selectedDate: (String) -> Unit,
    pegawaiListViewModel: MahasiswaListViewModel
) {
    val currentTimeMillis = System.currentTimeMillis()

    val datePickerState = remember {
        DatePickerState(
            initialSelectedDateMillis = currentTimeMillis,
            yearRange = 2000..2100,
            initialDisplayedMonthMillis = currentTimeMillis,
            locale = Locale.KOREA
        )
    }


    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
    var selectedDate by remember { mutableStateOf("") }


    LaunchedEffect (Unit){
        pegawaiListViewModel.changeTanggalSeharusnya(
            Instant.ofEpochMilli( System.currentTimeMillis())
                .atZone(ZoneId.of("Asia/Jakarta"))
                .toLocalDate()
                .format(dateFormatter)
        )
    }

    LaunchedEffect(key1 = Unit, key2 = datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val newDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of("Asia/Jakarta"))
                .toLocalDate()
                .format(dateFormatter)
            selectedDate = newDate  // Update selectedDate
            selectedDate(newDate)   // Kirim ke parameter selectedDate
            pegawaiListViewModel.changeTanggal(newDate)  // Perbarui ViewModel
        }
    }


    Box (
        modifier = Modifier
            .height(400.dp)
            .width(4000.dp)
    ){
        DatePicker(
            state = datePickerState,
            modifier = Modifier
                .offset(y= -32.dp)
                .scale(0.9f)
                .padding(1.dp),
            colors = DatePickerDefaults.colors(
                containerColor = Warna.PutihNormal, // Warna latar belakang
                titleContentColor = Warna.PutihNormal, // Warna judul
                headlineContentColor = Warna.MerahTua, // Warna tanggal terpilih
                weekdayContentColor = Color.Gray, // Warna nama hari (Sen, Sel, Rab, ...)
                subheadContentColor = Warna.HitamNormal, // Warna angka tanggal
                yearContentColor = Warna.MerahNormal, // Warna tahun
                todayContentColor = Color.Red, // Warna tanggal hari ini
                selectedDayContentColor = Color.White, // Warna angka tanggal yang dipilih
                selectedDayContainerColor = Warna.MerahTua// Warna latar tanggal yang dipilih
            )
        )
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = selectedDate,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Warna.MerahTua,


            )
    }



}