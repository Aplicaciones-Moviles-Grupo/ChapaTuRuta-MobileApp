package com.frock.chapaturuta.features.profile.domain.models

data class Profile(
    val id:Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val profileType: String,
    val profileImageUrl: String,
    val profileImagePublicId: String,
    val userId: Int
)
