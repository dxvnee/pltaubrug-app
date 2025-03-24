package org.d3if3121.absenubrugadmin.data.model

data class Jam(
    val masuk: String = "07:00",
    val keluar: String = "16:00"
){
    constructor(): this ("07:00", "16:00")

    companion object{
        const val MASUK = "masuk"
        const val KELUAR = "keluar"
    }
}
