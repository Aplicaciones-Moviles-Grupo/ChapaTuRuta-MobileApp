package com.frock.chapaturuta.features.auth.domain.repositories

import com.frock.chapaturuta.features.auth.domain.models.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User

    suspend fun register(email: String, password: String): User
}