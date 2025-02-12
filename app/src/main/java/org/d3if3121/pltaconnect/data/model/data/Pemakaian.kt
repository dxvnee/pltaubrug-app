package org.d3if3121.pltaconnect.data.model.data

data class Pemakaian (
    val id: String? = "",

    val tanggal: String? = "",
    val unit: String? = "",
    val kva: String? = "",

    val sesudah: String? = "",
    val sebelum: String? = "",

    val kwh: String? = "",
    val kumulatif: String? = "",

    val total: String? = "",

)

data class Total (
    val kwh: String? = ""
)
