package com.frock.chapaturuta.features.stops.data.repositories

import android.content.Context
import android.net.Uri
import com.frock.chapaturuta.features.stops.data.remote.services.GeocodingService
import com.frock.chapaturuta.features.stops.data.remote.services.StopService
import com.frock.chapaturuta.features.stops.domain.models.Stop
import com.frock.chapaturuta.features.stops.domain.repositories.StopRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class StopRepositoryImpl @Inject constructor(private val service: StopService,
                                             @ApplicationContext private val context: Context): StopRepository {

    override suspend fun createStop(
        stopImageUri: Uri?,
        name: String,
        address: String,
        latitude: Double,
        longitude: Double,
        driverId: Int
    ): Stop = withContext(Dispatchers.IO){
        try {

            val stopImage = stopImageUri?.let { uri ->
                prepareFilePart("StopImage",uri,context)
            }
            val name = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val address = address.toRequestBody("text/plain".toMediaTypeOrNull())
            val latitude = latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val longitude = longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val driverId = driverId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val response = service.createStop(stopImage,name,address,latitude,longitude,driverId)
            if(response.isSuccessful){
                response.body()?.let { stopDto ->
                    Stop(
                        stopDto.id,
                        stopDto.name,
                        stopDto.address,
                        stopDto.latitude,
                        stopDto.longitude,
                        stopDto.stopImageUrl,
                        stopDto.stopImagePublicId,
                        stopDto.driverId
                    )
                }?: throw Exception("Response is empty")
            } else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }

        }catch (e:Exception){
            throw Exception("Error al crear paradero: ${e.message}")
        }
    }

    override suspend fun updateStop(
        id: Int,
        stopImageUri: Uri?,
        name: String,
        address: String,
        latitude: Double,
        longitude: Double
    ): Stop = withContext(Dispatchers.IO) {
        try {
            val stopImage = stopImageUri?.let { uri ->
                prepareFilePart("StopImage",uri,context)
            }
            val name = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val address = address.toRequestBody("text/plain".toMediaTypeOrNull())
            val latitude = latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val longitude = longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val response = service.updateStop(id,stopImage,name,address,latitude,longitude)
            if(response.isSuccessful){
                response.body()?.let { stopDto ->
                    Stop(
                        stopDto.id,
                        stopDto.name,
                        stopDto.address,
                        stopDto.latitude,
                        stopDto.longitude,
                        stopDto.stopImageUrl,
                        stopDto.stopImagePublicId,
                        stopDto.driverId
                    )
                }?: throw Exception("Response is empty")
            } else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }

        }catch (e:Exception){
            throw Exception("Error al actualizar paradero: ${e.message}")
        }
    }

    override suspend fun getStopById(id: Int): Stop = withContext(Dispatchers.IO) {
        try {
            val response = service.getStopById(id)
            if(response.isSuccessful){
                response.body()?.let { stopDto ->
                    Stop(
                        stopDto.id,
                        stopDto.name,
                        stopDto.address,
                        stopDto.latitude,
                        stopDto.longitude,
                        stopDto.stopImageUrl,
                        stopDto.stopImagePublicId,
                        stopDto.driverId
                    )
                }?: throw Exception("Response is empty")
            } else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }

        }catch (e:Exception){
            throw Exception("Error al obtener paradero: ${e.message}")
        }
    }

    override suspend fun getStopsByDriverId(driverId: Int): List<Stop> = withContext(Dispatchers.IO) {
        try {
            val response = service.getAllStopsByDriverId(driverId)
            if(response.isSuccessful){
                response.body()?.let { stopsDto ->
                    return@withContext stopsDto.map { stopDto->
                        Stop(
                            stopDto.id,
                            stopDto.name,
                            stopDto.address,
                            stopDto.latitude,
                            stopDto.longitude,
                            stopDto.stopImageUrl,
                            stopDto.stopImagePublicId,
                            stopDto.driverId
                        )
                    }
                }
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }

        }catch (e:Exception){
            throw Exception("Error al obtener paraderos: ${e.message}")
        }
        return@withContext emptyList()
    }

    override suspend fun deleteStop(id: Int) = withContext(Dispatchers.IO){
        try {
            val response = service.deleteStop(id)
            if(!response.isSuccessful){
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error al eliminar paradero: ${e.message}")
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