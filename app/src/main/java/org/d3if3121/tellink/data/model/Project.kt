package org.d3if3121.tellink.data.model

import java.util.Date

data class Project(
    val id: String? = "",
    val nim: String,
    val title: String,
    val desc: String,
    val date: String? = "",
    val commentcount: String? = "",
    val requests: List<String>? = emptyList(),
    val accept: List<String>? = emptyList(),
    val image: String? = "",
    val tag: List<String>,
    val likes: Int? = 0,

    val imageupload: ImageUpload? = null
){
    constructor() : this("", "", "", "", "", "", emptyList(), emptyList(),"", emptyList(), 0)

    companion object {
        const val NIM = "nim"
        const val TITLE = "title"
        const val DESC = "desc"
        const val TAG = "tag"
        const val DATE = "date"
        const val IMAGE = "image"
        const val REQUESTS = "requests"
        const val ACCEPT = "accept"
    }
}