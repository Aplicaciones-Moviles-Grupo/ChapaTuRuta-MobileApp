package com.frock.chapaturuta.features.profile.presentation

import com.frock.chapaturuta.features.profile.domain.models.Profile

sealed class ProfileUiState {
    object Initial : ProfileUiState()
    object Loading : ProfileUiState()
    data class Success(val profile: Profile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}