package org.d3if3121.absenubrugadmin.data.model


data class Mahasiswa(
    val nip: String = "",
    val password: String = "",
    val nama: String = "",
    val foto: String? = null,
    val role: List<String> = emptyList(),
    val posisi: String = "-",
){

    constructor() : this("", "", "", "",emptyList(), "-")

    companion object {
        const val NIP = "nip"
        const val PASSWORD = "password"
        const val NAMA = "nama"
        const val FOTO = "foto"
        const val ROLE = "role"
        const val POSISI = "posisi"
    }
}