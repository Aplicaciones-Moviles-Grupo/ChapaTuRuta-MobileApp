package com.frock.chapaturuta.core.data.network

import com.frock.chapaturuta.core.data.datastore.AuthDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.text.isNullOrEmpty

class AuthInterceptor @Inject constructor(private val dataStore: AuthDataStore): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val token = runBlocking { dataStore.getToken() } //Lee el token desde el dataStore

        val newRequest = if(!token.isNullOrEmpty()){
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }else{
            request
        }

        return chain.proceed(newRequest);
    }
}