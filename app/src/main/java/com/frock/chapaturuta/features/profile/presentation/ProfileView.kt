package com.frock.chapaturuta.features.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.frock.chapaturuta.core.ui.components.ConfigurationCard
import com.frock.chapaturuta.core.ui.components.VehicleCard
import com.frock.chapaturuta.features.profile.domain.models.Profile

@Composable
fun ProfileView(

    userId:Int,
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditProfile:(profileId:Int)->Unit,
    onEditVehicle:(profileId:Int)->Unit){

    val profileUiState by viewModel.uiState.collectAsState()

    val vehicleUiState by viewModel.vehicleUiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getProfileByUserId(userId)
    }

    when(profileUiState){
        is ProfileUiState.Loading->{
            CircularProgressIndicator()
        }

        is ProfileUiState.Error -> {
            Text("Error: ${(profileUiState as ProfileUiState.Error).message}")
        }

        is ProfileUiState.Success->{

            val profile = (profileUiState as ProfileUiState.Success).profile

            viewModel.getVehicleByProfileId(profile.id)

            val vehicle = when(vehicleUiState){
                is VehicleUiState.Success -> (vehicleUiState as VehicleUiState.Success).vehicle
                else -> null
            }

            if(vehicleUiState is VehicleUiState.Error){
                Text("Error: ${(vehicleUiState as VehicleUiState.Error).message}")
            }
            if(vehicleUiState is VehicleUiState.Loading){
                CircularProgressIndicator()
            }



            Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

                Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        AsyncImage(
                            model = profile.profileImageUrl,
                            contentDescription = "",
                            modifier = Modifier.height(96.dp)
                        )
                    }

                    Text(
                        text = "${profile.firstName} ${profile.lastName}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = profile.email,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "My Vehicle",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                VehicleCard(vehicle)

                Text(
                    text = "Edit Configuration",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                ConfigurationCard("Edit Profile") { onEditProfile(profile.id)}
                ConfigurationCard("Edit Vehicle") { onEditVehicle(profile.id)}
            }
        }

        else -> {}
    }


}
