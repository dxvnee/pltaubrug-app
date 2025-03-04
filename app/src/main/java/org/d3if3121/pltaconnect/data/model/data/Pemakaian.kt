package org.d3if3121.pltaconnect.data.model.data

data class Pemakaian (
    val sesudah: String = "",
    val sebelum: String = "",
    val kwh: String = "",
)

data class PemakaianRequest (
    val id: String = "",

    val kwh1: String = "",
    val kwh1_sesudah: String = "",
    val kwh1_sebelum: String = "",

    val kwh2: String = "",
    val kwh2_sesudah: String = "",
    val kwh2_sebelum: String = "",

    val kwh3: String = "",
    val kwh3_sesudah: String = "",
    val kwh3_sebelum: String = ""
)
