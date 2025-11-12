package com.frock.chapaturuta.features.routes.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.frock.chapaturuta.core.ui.components.RouteCard
import com.frock.chapaturuta.R
import com.frock.chapaturuta.features.routes.domain.models.Route
import com.frock.chapaturuta.features.stops.domain.models.Stop
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.delay

@Composable
fun RoutesView(profileId:Int,
               routeViewModel: RouteViewModel = hiltViewModel(),
    onNavigateToCreateRoute: () -> Unit = {},
    onNavigateToEditRoute: (String) -> Unit = {}
) {

    val routes by routeViewModel.routes.collectAsState()
    val stopRoutes by routeViewModel.stopRoutes.collectAsState()

    var selectedRoute by remember { mutableStateOf<Route?>(null) }
    //var stops by remember { mutableStateOf<List<Stop>>(emptyList()) }

    // Estado de cámara del mapa
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }

    LaunchedEffect(profileId) {
        routeViewModel.getAllRoutes(profileId)
    }

    // Obtener los paraderos de la ruta seleccionada (cuando cambia)
    LaunchedEffect(selectedRoute) {
        selectedRoute?.let { route ->
            routeViewModel.getStopRoutesByRouteId(route.id)
            //delay(2000) // breve espera para cargar

            //stops = routeViewModel.stopRoutes.value.map { it.stop } // asumiendo que `StopRoute` contiene un campo `stop`
        }
    }

    val stops = stopRoutes.map { it.stop }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Routes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Create Route button
        Button(
            onClick = onNavigateToCreateRoute,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create Route", fontSize = 20.sp)
        }

        // 🔹 Mapa
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            // Si hay ruta seleccionada → dibujar polyline y marcadores
            selectedRoute?.let { route ->
                val path = PolyUtil.decode(route.polylineRoute)
                if (path.isNotEmpty()) {
                    Polyline(
                        points = path,
                        color = Color(0xFF6366F1),
                        width = 8f
                    )

                    // Mover la cámara para ajustar al recorrido
                    LaunchedEffect(path) {
                        val bounds = LatLngBounds.builder().apply {
                            path.forEach { include(it) }
                        }.build()
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngBounds(bounds, 100)
                        )
                    }

                    // Dibujar marcadores de paraderos
                    stops.forEach { stop ->
                        Marker(
                            state = rememberUpdatedMarkerState(position = LatLng(stop.latitude, stop.longitude)),
                            title = stop.name,
                            snippet = stop.address
                        )
                    }
                }
            }
        }
        // Routes list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(routes) { route ->
                RouteCard(route,
                    onClick = { selectedRoute = route },
                    onChangeState = {
                        Log.d("RoutesView", "🔁 onChangeState clicked for route ${route.id} (${route.state})")
                        if (route.state.equals("Active", ignoreCase = true)) {
                            routeViewModel.inactiveRoute(route.id)
                        } else {
                            routeViewModel.activeRoute(route.id)
                        }
                    })
            }
        }
    }
}