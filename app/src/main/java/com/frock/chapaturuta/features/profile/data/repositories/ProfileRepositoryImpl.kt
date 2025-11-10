package com.frock.chapaturuta.features.profile.data.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.frock.chapaturuta.features.profile.data.remote.services.ProfileService
import com.frock.chapaturuta.features.profile.domain.models.Profile
import com.frock.chapaturuta.features.profile.domain.models.Vehicle
import com.frock.chapaturuta.features.profile.domain.repositories.ProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val service: ProfileService, @ApplicationContext private val context: Context): ProfileRepository {

    override suspend fun getProfileByUserId(userId:Int): Profile = withContext(Dispatchers.IO) {
        try {
            val response = service.getProfileByUserId(userId)
            if(response.isSuccessful){
                response.body()?.let { profileDto ->
                    Profile(
                        profileDto.id,
                        profileDto.firstName,
                        profileDto.lastName,
                        profileDto.email,
                        profileDto.phoneNumber,
                        profileDto.profileType,
                        profileDto.profileImageUrl,
                        profileDto.profileImagePublicId,
                        profileDto.userId
                    )
                }?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error al registrar profile: ${e.message}")
        }
    }

    override suspend fun getProfile(id: Int): Profile = withContext(Dispatchers.IO) {
        try {
            val response = service.getProfile(id)
            if(response.isSuccessful){
                response.body()?.let { profileDto ->
                    Profile(
                        profileDto.id,
                        profileDto.firstName,
                        profileDto.lastName,
                        profileDto.email,
                        profileDto.phoneNumber,
                        profileDto.profileType,
                        profileDto.profileImageUrl,
                        profileDto.profileImagePublicId,
                        profileDto.userId
                    )
                }?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error al registrar profile: ${e.message}")
        }
    }


    override suspend fun updateProfile(
        id: Int,
        profileImageUri: Uri?,
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        profileType: String
    ): Profile = withContext(Dispatchers.IO) {
        try {
            val firstName = firstName.toRequestBody("text/plain".toMediaTypeOrNull())
            val lastName = lastName.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneNumber = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
            val profileType = profileType.toRequestBody("text/plain".toMediaTypeOrNull())

            val profileImage = profileImageUri?.let { uri->
                prepareFilePart("profileImage",uri,context)
            }

            val response = service.updateProfile(id, profileImage, firstName,lastName,email,phoneNumber,profileType)
            if(response.isSuccessful){
                response.body()?.let { profileDto ->
                    Profile(
                        profileDto.id,
                        profileDto.firstName,
                        profileDto.lastName,
                        profileDto.email,
                        profileDto.phoneNumber,
                        profileDto.profileType,
                        profileDto.profileImageUrl,
                        profileDto.profileImagePublicId,
                        profileDto.userId)
                }?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e: Exception){
            throw Exception("Error al actualizar profile: ${e.message}")
        }
    }

    override suspend fun createVehicle(
        vehicleImageUri: Uri?,
        plate: String,
        model: String,
        color: String,
        profileId: Int
    ): Vehicle = withContext(Dispatchers.IO) {

        Log.d("ProfileRepositoryImpl", "Llamando a API con profileId = $profileId")
        try {
            val vehicleImage = vehicleImageUri?.let { uri->
                prepareFilePart("vehicleImage",uri,context)
            }

            val plate = plate.toRequestBody("text/plain".toMediaTypeOrNull())
            val model = model.toRequestBody("text/plain".toMediaTypeOrNull())
            val color = color.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = service.createVehicle(profileId,vehicleImage,plate,model,color)

            if(response.isSuccessful){
                response.body()?.let { vehicleDto ->
                    Vehicle(
                        vehicleDto.id,
                        vehicleDto.plate,
                        vehicleDto.model,
                        vehicleDto.color,
                        vehicleDto.vehicleImageUrl,
                        vehicleDto.vehicleImagePublicId,
                        vehicleDto.profileId
                    )
                }?: throw Exception("Response is empty")
            }else {
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e: Exception){
            throw Exception("Error al crear vehicle: ${e.message}")
        }
    }

    override suspend fun getVehicleByProfileId(profileId: Int): Vehicle = withContext(Dispatchers.IO) {
        try {
            val response = service.getVehicleByProfileId(profileId)
            if(response.isSuccessful){
                response.body()?.let { vehicleDto->
                    Vehicle(
                        vehicleDto.id,
                        vehicleDto.plate,
                        vehicleDto.model,
                        vehicleDto.color,
                        vehicleDto.vehicleImageUrl,
                        vehicleDto.vehicleImagePublicId,
                        vehicleDto.profileId
                    )
                }?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e: Exception){
            throw Exception("Error al obtener vehicle: ${e.message}")
        }
    }

    override suspend fun updateVehicle(
        profileId: Int,
        id: Int,
        vehicleImageUri: Uri?,
        plate: String,
        model: String,
        color: String
    ): Vehicle= withContext(Dispatchers.IO){
        try {
            val vehicleImage = vehicleImageUri?.let { uri->
                prepareFilePart("vehicleImage",uri,context)
            }

            val plate = plate.toRequestBody("text/plain".toMediaTypeOrNull())
            val model = model.toRequestBody("text/plain".toMediaTypeOrNull())
            val color = color.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = service.updateVehicle(profileId,id,vehicleImage,plate,model,color)
            if(response.isSuccessful){
                response.body()?.let { vehicleDto->
                    Vehicle(
                        vehicleDto.id,
                        vehicleDto.plate,
                        vehicleDto.model,
                        vehicleDto.color,
                        vehicleDto.vehicleImageUrl,
                        vehicleDto.vehicleImagePublicId,
                        vehicleDto.profileId
                    )
                }?: throw Exception("Response is empty")
            }else {
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e: Exception){
            throw Exception("Error al actualizar vehicle: ${e.message}")
        }
    }

    private fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val type = contentResolver.getType(fileUri) ?: "image/*"
        val inputStream = contentResolver.openInputStream(fileUri)
        val file = File(context.cacheDir, "upload_image_${System.currentTimeMillis()}.jpg")
        inputStream?.use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }
        val requestFile = file.asRequestBody(type.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

}