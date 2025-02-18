package org.d3if3121.pltaconnect.data.model.data

data class Pemakaian (
    val sesudah: String = "",
    val sebelum: String = "",
    val kwh: String = "",
    val kwhkumulatif: String = "",
)

data class PemakaianRequest (
    val id: String = "",

    val kwh1: String = "",
    val kwh2: String = "",
    val kwh3: String = "",
)
