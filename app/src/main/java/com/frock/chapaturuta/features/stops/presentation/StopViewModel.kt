package com.frock.chapaturuta.features.stops.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frock.chapaturuta.features.stops.domain.models.Stop
import com.frock.chapaturuta.features.stops.domain.repositories.GeocodingRepository
import com.frock.chapaturuta.features.stops.domain.repositories.StopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopViewModel @Inject constructor(private val stopRepository: StopRepository,
    private val geocodingRepository: GeocodingRepository): ViewModel() {

    private val _stops = MutableStateFlow<List<Stop>>(emptyList())

    val stops: MutableStateFlow<List<Stop>> = _stops

    private val _address = MutableStateFlow<String>("")
    val address: MutableStateFlow<String> = _address

    private val _stop = MutableStateFlow<Stop?>(null)
    val stop: MutableStateFlow<Stop?> = _stop


     fun getAddressFromCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val result = geocodingRepository.getAddressFromLatLng(latitude, longitude)
            _address.value = result
        }
    }

    //private val _stopUiState = MutableStateFlow<StopUiState>(StopUiState.Initial)

    //val stopUiState: MutableStateFlow<StopUiState> = _stopUiState

    fun getAllStops(driverId:Int){
        viewModelScope.launch {
            _stops.value = stopRepository.getStopsByDriverId(driverId)
        }
    }


    fun createStop(stopImageUri: Uri?, name:String, address:String, latitude:Double, longitude:Double, driverId:Int){

        viewModelScope.launch {
            val stop = stopRepository.createStop(stopImageUri, name, address, latitude, longitude, driverId)
            _stop.value = stop

            _stops.value.toMutableList().apply {
                add(stop)
                _stops.value = this
            }
        }
    }

    fun deleteStop(stopId:Int){
        viewModelScope.launch {
            stopRepository.deleteStop(stopId)
            _stops.value = _stops.value.filter { it.id != stopId }
        }
    }
}