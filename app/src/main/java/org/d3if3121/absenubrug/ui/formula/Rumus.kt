package org.d3if3121.absenubrug.ui.formula

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import org.d3if3121.absenubrug.data.model.Absen
import org.d3if3121.absenubrug.data.model.ImageUpload
import org.d3if3121.absenubrug.data.model.Mahasiswa
import org.d3if3121.absenubrug.data.model.Response
import org.d3if3121.absenubrug.navigation.Screen
import org.d3if3121.absenubrug.ui.viewmodel.MahasiswaListViewModel
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    viewmodel: MahasiswaListViewModel,
    onLocationReceived: (Double, Double) -> Unit,
) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        Log.e("getCurrentLocation", "Izin lokasi tidak diberikan!")
        return
    }

    viewmodel.changeLoading(true)
    Log.d("getCurrentLocation", "Mencoba mendapatkan lokasi terbaru...")

    val cancellationTokenSource = CancellationTokenSource()
    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
        .addOnSuccessListener { location: Location? ->
            if (location != null) {
                viewmodel.changeLoading(false)
                Log.d("getCurrentLocation", "Lokasi ditemukan: ${location.latitude}, ${location.longitude}")
                onLocationReceived(location.latitude, location.longitude)
            } else {
                Log.e("getCurrentLocation", "Lokasi tidak tersedia, coba metode lain")
            }
        }
        .addOnFailureListener { e ->
            Log.e("getCurrentLocation", "Gagal mendapatkan lokasi: ${e.message}")
        }
}


fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var fileName: String? = null
    val cursor = context.contentResolver.query(uri, null, null, null, null)

    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                fileName = it.getString(nameIndex)
            }
        }
    }

    return fileName
}

fun dapatJam(): String {
    val currentTimeMillis = System.currentTimeMillis()
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(currentTimeMillis))

}

fun isInsideRadiusAndroid(currentLat: Double, currentLng: Double): Boolean {
    val targetLocation = Location("").apply {
        latitude = -6.949650
        longitude = 106.755531
    }

    val currentLocation = Location("").apply {
        latitude = currentLat
        longitude = currentLng
    }

    val distance = currentLocation.distanceTo(targetLocation)

    return distance <= 71.0
}

fun isInsideTriangle(currentLat: Double, currentLng: Double): Boolean {
    val ax = -6.947752
    val ay = 106.757868
    val bx = -6.948594
    val by = 106.754947
    val cx = -6.950487
    val cy = 106.754995

    fun area(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Double {
        return 0.5 * kotlin.math.abs((x1 * (y2 - y3)) + (x2 * (y3 - y1)) + (x3 * (y1 - y2)))
    }

    val areaABC = area(ax, ay, bx, by, cx, cy)

    val areaPBC = area(currentLat, currentLng, bx, by, cx, cy)
    val areaPCA = area(ax, ay, currentLat, currentLng, cx, cy)
    val areaPAB = area(ax, ay, bx, by, currentLat, currentLng)

    return kotlin.math.abs(areaABC - (areaPBC + areaPCA + areaPAB)) < 1e-9
}



fun parseJam(jam: String): LocalTime? {
    return if (isValidTimeFormat(jam)) {
        try {
            LocalTime.parse(jam, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}

fun isValidTimeFormat(jam: String): Boolean {
    val regex = Regex("^\\d{2}:\\d{2}$")
    return regex.matches(jam)
}

fun selisihJam(waktu1: String, waktu2: String): String {
    val time1 = parseJam(waktu1)
    val time2 = parseJam(waktu2)

    Log.d("wWAKTU", "$time1 $time2")
    return if (time1 != null && time2 != null) {
        val durasi = Duration.between(time1, time2)
        val jam = durasi.toHours()
        val menit = durasi.toMinutes() % 60

        "$jam jam $menit menit"
    } else {
        "Format Salah (HH:mm)"
    }
}

fun isLebihCepat(waktu1: String, waktu2: String): Boolean {
    val time1 = parseJam(waktu1)
    val time2 = parseJam(waktu2)

    return if (time1 != null && time2 != null) {
        time1.isBefore(time2)
    } else {
        false
    }
}

fun createAbsen(
    user: Mahasiswa,
    keterangan: String,
    lokasi: String,
    foto: ImageUpload?,
    deskripsi: String,
    tanggal: String,
    jam: String,
    telat: String,
    jamtelat: String,
    absen: String,
    isPulang: String
): Absen {
    return if (isPulang == "true") {
        Absen(
            nip = user.nip,
            tanggal = tanggal,
            keterangan2 = keterangan,
            lokasi2 = lokasi,
            foto2 = foto,
            deskripsi2 = deskripsi,
            jam2 = jam,
            telat2 = telat,
            jamtelat2 = jamtelat,
            absen2 = absen
        )
    } else if (isPulang == "false") {
        Absen(
            nama = user.nama,
            nip = user.nip,
            tanggal = tanggal,
            keterangan = keterangan,
            lokasi = lokasi,
            foto = foto,
            deskripsi = deskripsi,
            jam = jam,
            telat = telat,
            jamtelat = jamtelat,
            absen = absen
        )
    } else {
        Absen(
            nama = user.nama,
            nip = user.nip,
            tanggal = tanggal,
            keterangan = keterangan,
            lokasi = lokasi,
            foto = foto,
            deskripsi = deskripsi,
            jam = jam,
            telat = telat,
            jamtelat = jamtelat,
            absen = absen,

            keterangan2 = keterangan,
            lokasi2 = lokasi,
            foto2 = foto,
            deskripsi2 = deskripsi,
            jam2 = jam,
            telat2 = telat,
            jamtelat2 = jamtelat,
            absen2 = absen
        )
    }
}



