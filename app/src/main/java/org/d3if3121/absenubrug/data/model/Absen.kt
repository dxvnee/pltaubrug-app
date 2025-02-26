package org.d3if3121.absenubrug.data.model


data class Absen(
    val nama: String = "",
    val absen: String = "",
    val absen2: String = "",
    val nip: String = "",
    val keterangan: String = "Belum Absen",
    val deskripsi: String = "",
    val tanggal: String = "",
    val jam: String = "-",
    val telat: String = "",
    val jamtelat: String? = "",
    val image: String? = "",

    val lokasi: String = "",
    val foto: ImageUpload? = null,

    val keterangan2: String = "Belum Absen",
    val deskripsi2: String = "",
    val jam2: String = "-",
    val lokasi2: String = "",
    val foto2: ImageUpload? = null,
    val telat2: String = "",
    val jamtelat2: String? = "",
){
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", null, "", "", "", "", null, "", "")
}

