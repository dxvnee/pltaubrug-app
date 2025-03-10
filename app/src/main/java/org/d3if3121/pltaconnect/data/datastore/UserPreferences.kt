package org.d3if3121.pltaconnect.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences @Inject constructor(private val context: Context) {
    companion object{
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val IS_LOGGED_IN_KEY = stringPreferencesKey("is_logged_in")
        private val SCRIPT_LINK_KEY = stringPreferencesKey("script_link")
    }

    val userid: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }
    val password: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PASSWORD_KEY]
    }


    val isloggedin: Flow<Boolean> = context.dataStore.data.map { prefs ->
        (prefs[IS_LOGGED_IN_KEY] ?: "false").toBoolean()
    }


    suspend fun saveuser(userid: String, password: String){
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userid
            prefs[PASSWORD_KEY] = password
            prefs[IS_LOGGED_IN_KEY] = "true"
            Log.d("jalanle",  prefs[IS_LOGGED_IN_KEY].toString())
        }
    }

    suspend fun clearuser() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
            prefs.remove(PASSWORD_KEY)
            prefs[IS_LOGGED_IN_KEY] = "false"
            Log.d("jalanle",  prefs[IS_LOGGED_IN_KEY].toString())

        }
    }
}