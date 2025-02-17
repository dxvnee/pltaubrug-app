package org.d3if3121.pltaconnect.data.repository.interfaces

import com.google.auth.oauth2.GoogleCredentials
import org.d3if3121.pltaconnect.data.model.AppsScriptResponse
import org.d3if3121.pltaconnect.data.model.ScriptRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AppsScriptService {
    @POST("macros/s/AKfycbyittQF5xrqMqjp97eTIBE3oQnT-4Gus8og1z0lC6RVYWRrsR5crEk4MCwJ4A42wWQ/exec")
    suspend fun runScript(): AppsScriptResponse

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

suspend fun getServiceAccountAccessToken(): String? {
    val jsonKey = "YOUR_SERVICE_ACCOUNT_JSON"
    val googleCredentials = GoogleCredentials.fromStream(jsonKey.byteInputStream())
        .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))
        .refreshAccessToken()

    return googleCredentials.tokenValue
}






