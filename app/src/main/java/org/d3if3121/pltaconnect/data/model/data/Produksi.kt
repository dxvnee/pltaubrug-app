package org.d3if3121.pltaconnect.data.model.data

data class Produksi(
    var sesudah: String = "",
    var sebelum: String = "",
    var selisih: String = "0.000",
    var kwh: String = "0.000",
    var rataair: String = "0.000",
    var air: String = "",
    var ekonomisair: String = "",
    var pemakaian: String = "0.0",
    var penjualan: String = ""
)

data class ProduksiRequest(
    var id: String,
    var sesudah1: String = "",
    var sebelum1: String = "",
    var selisih1: String = "",
    var kwh1: String = "",
    var rataair1: String = "",
    var air1: String = "",
    var ekonomisair1: String = "",
    var pemakaian1: String = "",
    var penjualan1: String = "",

    var sesudah2: String = "",
    var sebelum2: String = "",
    var selisih2: String = "",
    var kwh2: String = "",
    var rataair2: String = "",
    var air2: String = "",
    var ekonomisair2: String = "",
    var pemakaian2: String = "",
    var penjualan2: String = "",

    var sesudah3: String = "",
    var sebelum3: String = "",
    var selisih3: String = "",
    var kwh3: String = "",
    var rataair3: String = "",
    var air3: String = "",
    var ekonomisair3: String = "",
    var pemakaian3: String = "",
    var penjualan3: String = ""
)
