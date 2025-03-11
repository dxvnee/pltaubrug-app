package org.d3if3121.absenubrug.ui.component

import org.d3if3121.absenubrug.ui.theme.Warna


import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    selectedDate: (String) -> Unit,
    pegawaiListViewModel: MahasiswaListViewModel
) {
    val currentTimeMillis = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        .toInstant()
        .toEpochMilli()


    val datePickerState = remember {
        DatePickerState(
            initialSelectedDateMillis = currentTimeMillis,
            initialDisplayedMonthMillis = currentTimeMillis,
            locale = Locale.ENGLISH
        )
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
    var selectedDate by remember { mutableStateOf(Instant.ofEpochMilli(currentTimeMillis)
        .atZone(ZoneId.of("Asia/Jakarta"))
        .toLocalDate()
        .format(dateFormatter)) }


    LaunchedEffect (Unit){
        pegawaiListViewModel.changeTanggalSeharusnya(
            Instant.ofEpochMilli( System.currentTimeMillis())
                .atZone(ZoneId.of("Asia/Jakarta"))
                .toLocalDate()
                .format(dateFormatter)
        )
    }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val newDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of("Asia/Jakarta"))
                .toLocalDate()
                .format(dateFormatter)

            if (newDate != selectedDate) {
                selectedDate = newDate
                selectedDate(selectedDate)
                pegawaiListViewModel.changeTanggal(newDate)
            }
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