package org.d3if3121.pltaconnect.data.model

sealed class Response<out T> {
    data object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?,
        val idsheet: T? = null
    ) : Response<T>()

    data class Failure(
        val e: Exception?
    ) : Response<Nothing>()
}