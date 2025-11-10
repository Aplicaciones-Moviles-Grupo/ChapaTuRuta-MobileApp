package com.frock.chapaturuta.features.profile.domain.repositories

import android.net.Uri
import com.frock.chapaturuta.features.profile.domain.models.Profile
import com.frock.chapaturuta.features.profile.domain.models.Vehicle

interface ProfileRepository {

    suspend fun getProfileByUserId(userId:Int): Profile

    suspend fun getProfile(id:Int): Profile

    suspend fun updateProfile(id:Int,profileImageUri: Uri?,firstName:String, lastName:String, email:String, phoneNumber:String, profileType:String):Profile

    suspend fun createVehicle(vehicleImageUri:Uri?, plate:String, model:String, color:String, profileId:Int): Vehicle

    suspend fun getVehicleByProfileId(profileId:Int): Vehicle

    suspend fun updateVehicle(profileId:Int, id:Int, vehicleImageUri:Uri?, plate:String, model:String, color:String): Vehicle
}