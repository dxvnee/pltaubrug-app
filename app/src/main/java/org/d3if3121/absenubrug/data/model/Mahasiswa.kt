package org.d3if3121.absenubrug.data.model


data class Mahasiswa(
    val nip: String = "",
    val password: String = "",
    val nama: String = "",
    val role: List<String> = emptyList(),
    val foto: String = "",
    val posisi: String = "-",
){

    constructor() : this("", "", "", emptyList<String>(), "", "-")

    companion object {
        const val NIP = "nip"
        const val PASSWORD = "password"
        const val NAMA = "nama"
        const val ROLE = "role"
        const val FOTO = "foto"
        const val POSISI = "posisi"
    }
}
