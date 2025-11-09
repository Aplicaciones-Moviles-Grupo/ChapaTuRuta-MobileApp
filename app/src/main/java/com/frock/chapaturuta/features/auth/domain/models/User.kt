package com.frock.chapaturuta.features.auth.domain.models

data class User(
    val id:Int,
    val email:String,
    val token:String
)
