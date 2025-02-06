package org.d3if3121.tellink.data.model

data class MahasiswaLogin(
    val nim: String,
    val password: String
){
    companion object {
        const val NIM = "nim"
        const val PASSWORD = "password"
    }
}