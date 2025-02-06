package org.d3if3121.tellink.data.model


data class Mahasiswa(
    val nim: String = "",
    val password: String = "",
    val nama: String = "",
    val jurusan: String = "",
    val angkatan: String? = null,
    val requests: List<String>? = emptyList(),
    val accept: List<String>? = emptyList(),

    val posts: List<String>? = null,
    val totalpost: Int? = null
){

    constructor() : this("", "", "", "", "", emptyList(), emptyList(), emptyList(),0)

    companion object {
        const val NIM = "nim"
        const val PASSWORD = "password"
        const val NAMA = "nama"
        const val JURUSAN = "jurusan"
        const val ANGKATAN = "angkatan"
        const val POSTS = "posts"
        const val TOTALPOST = "totalpost"
        const val REQUESTS = "requests"
        const val ACCEPT = "accept"
    }
}
