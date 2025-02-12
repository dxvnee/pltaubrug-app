package org.d3if3121.pltaconnect.data.repository.interfaces

import org.d3if3121.pltaconnect.data.model.AppsScriptResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppsScriptService {
    @POST("v1/scripts/AKfycbcVtoDjj8VDtoPzmwzFZP131DHcZhlsfpnTdqUB4fVDAR2jCNf7DfMsqnmM_fqYEsQ:run")
    suspend fun runScript(
        @Header("Authorization") token: String,
        @Body request: Map<String, Any>  // Gunakan Any untuk menangani berbagai tipe data
    ): AppsScriptResponse

}

object RetrofitClient {
    val instance: AppsScriptService by lazy {
        Retrofit.Builder()
            .baseUrl("https://script.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppsScriptService::class.java)
    }
}

