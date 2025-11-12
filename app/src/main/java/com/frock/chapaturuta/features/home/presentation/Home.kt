package com.frock.chapaturuta.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.frock.chapaturuta.core.ui.components.RouteCard
import com.frock.chapaturuta.core.ui.components.StopCard
import com.frock.chapaturuta.core.ui.components.VehicleCard
import com.frock.chapaturuta.core.ui.theme.AppTheme
import com.frock.chapaturuta.features.profile.presentation.ProfileUiState
import com.frock.chapaturuta.features.profile.presentation.ProfileViewModel
import com.frock.chapaturuta.features.profile.presentation.VehicleUiState
import com.frock.chapaturuta.features.routes.presentation.RouteViewModel
import com.frock.chapaturuta.features.stops.presentation.StopViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun Home(userId:Int,
         profileViewModel: ProfileViewModel = hiltViewModel(),
         stopViewModel: StopViewModel = hiltViewModel(),
         routeViewModel: RouteViewModel= hiltViewModel()){

    LaunchedEffect(userId) {
        profileViewModel.getProfileByUserId(userId)
        profileViewModel.getVehicleByProfileId(userId)
        routeViewModel.getAllRoutes(userId)
        stopViewModel.getAllStops(userId)
    }

    val profileUiState by profileViewModel.uiState.collectAsState()
    val vehicleUiState by profileViewModel.vehicleUiState.collectAsState()

    val stops by stopViewModel.stops.collectAsState()
    val routes by routeViewModel.routes.collectAsState()

    when(profileUiState){
        is ProfileUiState.Loading ->{
            CircularProgressIndicator()
        }
        is ProfileUiState.Error -> {
            Text("Error: ${(profileUiState as ProfileUiState.Error).message}")
        }
        is ProfileUiState.Success ->{
            val profile = (profileUiState as ProfileUiState.Success).profile
            val vehicle = when(vehicleUiState){
                is VehicleUiState.Success -> (vehicleUiState as VehicleUiState.Success).vehicle
                else -> null
            }

            Column(modifier = Modifier.padding(8.dp,8.dp).fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                Row(modifier = Modifier.height(64.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center){

                        AsyncImage(model = profile.profileImageUrl,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Column(modifier = Modifier.weight(1f),verticalArrangement = Arrangement.Center) {
                        Text(
                            text = "Hello, ${profile.firstName} ${profile.lastName}",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 24.sp
                        )
                        Text(
                            text = "Good Morning!",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }

                }

                Row(modifier = Modifier.height(48.dp).fillMaxWidth().padding(8.dp,4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Vehicle",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {}, contentPadding = PaddingValues(0.dp,0.dp)) {
                        Text(text = "See Details")
                    }
                }

                VehicleCard(vehicle)

                Row(modifier = Modifier.height(48.dp).fillMaxWidth().padding(8.dp,4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Let's Drive",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {}, contentPadding = PaddingValues(0.dp,0.dp)) {
                        Text(text = "See All")
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier= Modifier.fillMaxWidth().weight(1f)
                ){
                    items(routes){ route->
                        RouteCard(route,onChangeState = {
                            if (route.state.equals("Active", ignoreCase = true)) {
                                routeViewModel.inactiveRoute(route.id)
                            } else {
                                routeViewModel.activeRoute(route.id)
                            }
                        })
                    }
                }
                //RouteCard()
                //RouteCard()

                Row(modifier = Modifier.height(48.dp).fillMaxWidth().padding(8.dp,4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Your Stops",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {}, contentPadding = PaddingValues(0.dp,0.dp)) {
                        Text(text = "See All")
                    }
                }

                // Stops list
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier= Modifier.fillMaxWidth().weight(1f)
                ) {
                    items(stops) { stop ->
                        StopCard(
                            stop = stop,
                            onDelete = { stopViewModel.deleteStop(stop.id) }
                        )
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
@Preview
fun HomePreview(){
    AppTheme(dynamicColor = false) {
        //Home()
    }
}