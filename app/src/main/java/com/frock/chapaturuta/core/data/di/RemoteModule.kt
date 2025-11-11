package com.frock.chapaturuta.core.data.di

import com.frock.chapaturuta.core.data.network.AuthInterceptor
import com.frock.chapaturuta.features.auth.data.remote.services.AuthService
import com.frock.chapaturuta.features.profile.data.remote.services.ProfileService
import com.frock.chapaturuta.features.stops.data.remote.services.GeocodingService
import com.frock.chapaturuta.features.stops.data.remote.services.StopService
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
    @Named("backend_url")
    fun provideApiBaseUrl():String{
        return "http://10.0.2.2:5042/api/v1/"
    }

    @Provides
    @Singleton
    @Named("geocoding_url")
    fun provideGeocodingBaseUrl(): String {
        return "https://maps.googleapis.com/"
    }

    // 🔹 Cliente HTTP para Geocoding (sin interceptor de autenticación)
    @Provides
    @Singleton
    @Named("geocoding_client")
    fun provideGeocodingOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    @Named("backend_client")
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    @Named("backend_retrofit")
    fun provideRetrofit(
        @Named("backend_url") url:String,
        @Named("backend_client") okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("geocoding_retrofit")
    fun provideGeocodingRetrofit(
        @Named("geocoding_url") url: String,
        @Named("geocoding_client") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideAuthService(@Named("backend_retrofit") retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(@Named("backend_retrofit") retrofit: Retrofit): ProfileService{
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideStopService(@Named("backend_retrofit") retrofit: Retrofit): StopService {
        return retrofit.create(StopService::class.java)
    }


    @Provides
    @Singleton
    fun provideGeocodingService(@Named("geocoding_retrofit") retrofit: Retrofit): GeocodingService {
        return retrofit.create(GeocodingService::class.java)
    }
}