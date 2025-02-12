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
    @POST("v1/scripts/AKfycby5wHLznJNNr7KTb5pC_fAkFsqWv-9t7efF0m3_ESRRoGC9zECdeu1RQvBdPdUBHg:run")
    suspend fun runScript(
        @Header("Authorization") token: String,
        @Body request: ScriptRequest
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

suspend fun getServiceAccountAccessToken(): String? {
    val jsonKey = "YOUR_SERVICE_ACCOUNT_JSON"
    val googleCredentials = GoogleCredentials.fromStream(jsonKey.byteInputStream())
        .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))
        .refreshAccessToken()

    return googleCredentials.tokenValue
}






