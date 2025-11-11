package com.frock.chapaturuta.features.stops.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.frock.chapaturuta.core.ui.components.StopCard
import com.frock.chapaturuta.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch

@Composable
fun StopsView(
    profileId:Int,
    onNavigateToCreateStop: () -> Unit = {},
    onNavigateToEditStop: (String) -> Unit = {},
    viewModel: StopViewModel = hiltViewModel()
) {

    val stops by viewModel.stops.collectAsState()

    // Cámara inicial
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }
    var tempLatLng by remember { mutableStateOf<LatLng?>(null) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(profileId) {
        viewModel.getAllStops(profileId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Stops",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Create Stop button
        Button(
            onClick = onNavigateToCreateStop,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create Stop", fontSize = 16.sp)
        }


        // Mapa
        GoogleMap(
            modifier = Modifier.fillMaxWidth().weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            // Dibujamos todos los paraderos
            stops.forEach { stop ->
                Marker(
                    state = rememberUpdatedMarkerState(position = LatLng(stop.latitude, stop.longitude)),
                    title = stop.name,
                    snippet = stop.address
                )
            }
        }

        // Stops list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier= Modifier.height(180.dp)
        ) {
            items(stops) { stop ->
                StopCard(
                    stop = stop,
                    onDelete = { viewModel.deleteStop(stop.id) },
                    onEdit = { onNavigateToEditStop(stop.name) },
                    onSelect = {
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(
                                    LatLng(stop.latitude, stop.longitude),
                                    16f // nivel de zoom deseado
                                ),
                                durationMs = 1000
                            )
                        }
                    }
                )
            }
        }
    }
}