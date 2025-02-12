package org.d3if3121.pltaconnect.data.model.data

data class Produksi (
    val id: String? = "",

    val tanggal: String? = "",
    val jam: String? = "",
    val unit: String? = "",

    val sesudahi: String? = "",
    val sebelum: String? = "",
    val selisih: String? = "",
    val kwh: String? = "",

    val penggunaan: String? = "",
    val ekonomis: String? = "",
    val rata2: String? = "",

    val pemakaian: String? = "",
    val penjualan: String? = "",

)


data class TotalProduksi (
    val produksi: String? = "",
    val pemakaian: String? = "",
    val penggunaan: String? = "",
    val rata2: String? = "",
    val ekonomis: String? = "",
    val penjualan: String? = "",
)

