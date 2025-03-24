package org.d3if3121.absenubrugadmin.data.datastore

import android.content.Context
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
        private val IS_LOGGED_IN_KEY = stringPreferencesKey("is_logged_in")
    }

    val userid: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }
    val isloggedin: Flow<Boolean> = context.dataStore.data.map { prefs ->
        (prefs[IS_LOGGED_IN_KEY] ?: "false").toBoolean()
    }

    suspend fun saveuser(userid: String){
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userid
            prefs[IS_LOGGED_IN_KEY] = "true"
        }
    }
    suspend fun clearuser() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
            prefs[IS_LOGGED_IN_KEY] = "false"
        }
    }
}

