package com.frock.chapaturuta.core.data.di

import com.frock.chapaturuta.features.auth.data.repositories.AuthRepositoryImpl
import com.frock.chapaturuta.features.auth.domain.repositories.AuthRepository
import com.frock.chapaturuta.features.profile.data.repositories.ProfileRepositoryImpl
import com.frock.chapaturuta.features.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}