package org.d3if3121.pltaconnect.data.model

data class PegawaiLogin(
    val nim: String,
    val password: String
){
    companion object {
        const val NIM = "nim"
        const val PASSWORD = "password"
    }
}