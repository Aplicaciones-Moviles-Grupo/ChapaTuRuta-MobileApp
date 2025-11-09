package com.frock.chapaturuta.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class AuthDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun saveToken(token:String){
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun getToken():String?{
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    suspend fun clearToken(){
        context.dataStore.edit { prefs->
            prefs.remove(TOKEN_KEY)
        }
    }

    companion object{
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }
}

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")