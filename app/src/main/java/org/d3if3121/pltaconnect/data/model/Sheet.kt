package org.d3if3121.pltaconnect.data.model

data class Sheet (
    val id: String,
    val link: String
) {
    constructor() : this("", "")
}