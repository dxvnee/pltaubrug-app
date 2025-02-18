package org.d3if3121.pltaconnect.data.model.data

data class Masuk (
    val jam: String = "00:00",
    val keterangan: String = "",

    val masuk1: String = "00:00",
    val keluar1: String = "00:00",

    val masuk2: String = "00:00",
    val keluar2: String = "00:00",

    val masuk3: String = "00:00",
    val keluar3: String = "00:00",

)

data class MasukRequest (
    val id: String = "",

    val jam1: String = "",
    val masuk1_1: String = "",
    val keluar1_1: String = "",

    val masuk1_2: String = "",
    val keluar1_2: String = "",

    val masuk1_3: String = "",
    val keluar1_3: String = "",

    val keterangan1: String = "",

    val jam2: String = "",
    val masuk2_1: String = "",
    val keluar2_1: String = "",

    val masuk2_2: String = "",
    val keluar2_2: String = "",

    val masuk2_3: String = "",
    val keluar2_3: String = "",

    val keterangan2: String = "",

    val jam3: String = "",
    val masuk3_1: String = "",
    val keluar3_1: String = "",

    val masuk3_2: String = "",
    val keluar3_2: String = "",

    val masuk3_3: String = "",
    val keluar3_3: String = "",

    val keterangan3: String = "",
)

