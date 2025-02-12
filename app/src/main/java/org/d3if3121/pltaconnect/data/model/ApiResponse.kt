package org.d3if3121.pltaconnect.data.model

data class AppsScriptResponse(
    val done: Boolean,
    val response: ResponseData?
)

data class ResponseData(
    val result: String
)

data class ScriptRequest(
    val function: String
)

