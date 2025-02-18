package org.d3if3121.pltaconnect.data.repository.interfaces

import com.google.auth.oauth2.GoogleCredentials
import okhttp3.ResponseBody
import org.d3if3121.pltaconnect.data.model.AppsScriptResponse
import org.d3if3121.pltaconnect.data.model.ScriptRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface AppsScriptService {
    @POST
    suspend fun runScript(@Url url: String): ResponseBody

}

object RetrofitClient {
    val instance: AppsScriptService by lazy {
        Retrofit.Builder()
            .baseUrl("https://script.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppsScriptService::class.java)
    }
}







