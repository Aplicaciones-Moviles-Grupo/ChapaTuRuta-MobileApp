package com.frock.chapaturuta.core.data.di

import com.frock.chapaturuta.core.data.network.AuthInterceptor
import com.frock.chapaturuta.features.auth.data.remote.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {


    @Provides
    @Singleton
    @Named("url")
    fun provideApiBaseUrl():String{
        return "http://10.0.2.2:5042/api/v1/"
    }

    @Provides
    @Singleton
    fun provieOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named("url") url:String, okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    }

}