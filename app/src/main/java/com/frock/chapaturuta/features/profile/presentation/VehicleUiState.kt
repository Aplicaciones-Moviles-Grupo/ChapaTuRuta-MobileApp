package com.frock.chapaturuta.features.profile.presentation

import com.frock.chapaturuta.features.profile.domain.models.Vehicle

sealed class VehicleUiState {
    object Initial : VehicleUiState()
    object Loading : VehicleUiState()
    data class Success(val vehicle: Vehicle) : VehicleUiState()
    data class Error(val message: String) : VehicleUiState()
}