package com.frock.chapaturuta.features.routes.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.frock.chapaturuta.R
import com.frock.chapaturuta.features.stops.domain.models.Stop
import com.frock.chapaturuta.features.stops.presentation.StopViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun CreateRouteView(profileId:Int,
                    routeViewModel: RouteViewModel = hiltViewModel(),
                    stopViewModel: StopViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {

    var routeName by remember { mutableStateOf("") }
    /*val stops = remember {
        mutableStateListOf(
            StopItem("Stop Name #01", "Av. Javier Prado 123", false),
            StopItem("Stop Name #02", "Av. Javier Prado 123", false),
            StopItem("Stop Name #03", "Av. Javier Prado 123", false)
        )
    }*/

    var showDialog by remember { mutableStateOf(false) }

    val directionsResult by routeViewModel.directionsResult.collectAsState()
    val route by routeViewModel.route.collectAsState()
    val stopRoutes by routeViewModel.stopRoutes.collectAsState()
    val stopRoute by routeViewModel.stopRoute.collectAsState()

    //Posicion de la camara del Map
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }

    var price by remember { mutableStateOf("") }

    val stops by stopViewModel.stops.collectAsState()

    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    val selectedStops = stops.filter { selectedIds.contains(it.id) }

    val coroutineScope = rememberCoroutineScope()




    LaunchedEffect(profileId) {
        stopViewModel.getAllStops(profileId)
    }

    // 🧭 Decodificar la ruta (fuera del GoogleMap)
    val path = remember(directionsResult) {
        directionsResult?.polylinePoints?.let { PolyUtil.decode(it) } ?: emptyList()
    }

// 📸 Centrar la cámara cuando haya una ruta
    LaunchedEffect(path) {
        if (path.isNotEmpty()) {
            val bounds = LatLngBounds.builder().apply {
                path.forEach { include(it) }
            }.build()
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 100)
            )
        }
    }

    Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF5F5F5))
        .padding(16.dp)
    ) {
        Text(
            text = "Create Route",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )



        Spacer(modifier = Modifier.height(12.dp))
        Text("Seleccionados: ${selectedStops.size}")

        //GoogleMap
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            // Dibujamos todos los paraderos
            selectedStops.forEach { stop ->
                Marker(
                    state = rememberUpdatedMarkerState(position = LatLng(stop.latitude, stop.longitude)),
                    title = stop.name,
                    snippet = stop.address
                )
            }

            // Dibujar la línea de la ruta si existe
            if (path.isNotEmpty()) {
                Polyline(
                    points = path,
                    color = Color(0xFF6366F1),
                    width = 8f
                )
            }
        }

        Button(onClick = {showDialog = true},modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
            shape = RoundedCornerShape(12.dp)) {
            Text("Create Route")
        }

        TextButton(
            onClick = {
                price = ""
                routeName = ""
                onNavigateBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Cancel", fontSize = 16.sp, color = Color.Gray)
        }

        // Dialog para registrar un nuevo paradero
        if (showDialog) {
            Dialog(
                onDismissRequest = {showDialog = false}
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)  // 👈 ancho del diálogo (90% de pantalla)
                        .fillMaxHeight(0.8f) // 👈 alto del diálogo (80% de pantalla)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 8.dp,
                    color = Color.White
                ){

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Route Name input
                        OutlinedTextField(
                            value = routeName,
                            onValueChange = { routeName = it },
                            placeholder = { Text("Route Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            )
                        )

                        // Price input
                        OutlinedTextField(
                            value = price,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            onValueChange = { newValue ->
                                // Solo permitir números y punto decimal (opcional)
                                if (newValue.all { it.isDigit() || it == '.' }) {
                                    price = newValue
                                }
                            },
                            label = { Text("Price (S/)") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            singleLine = true
                        )

                        Text(
                            text = "Select your stops",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Stops list
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(stops){ stop ->
                                StopSelectItem(stop,selectedIds.contains(stop.id), onCheckedChange = {
                                        checked ->
                                    selectedIds = if (checked) {
                                        selectedIds + stop.id
                                    } else {
                                        selectedIds - stop.id
                                    }
                                })
                            }
                            /*items(stops.size) { index ->
                                StopSelectItem(
                                    stop = stops[index],
                                    onToggle = { stops[index] = stops[index].copy(selected = !stops[index].selected) }
                                )
                            }*/
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ){
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        // 1️⃣ Llamar a getDirections y esperar que termine
                                        routeViewModel.getDirections(selectedStops)// Espera hasta que termine
                                        // 2️⃣ Esperar hasta que directionsResult tenga datos
                                        var attempts = 0
                                        while (routeViewModel.directionsResult.value == null && attempts < 20) {
                                            delay(250) // espera 0.25s por intento
                                            attempts++
                                        }

                                        val result = routeViewModel.directionsResult.value
                                        if (result == null) {
                                            Log.e("CreateRoute", "❌ No se pudo obtener directionsResult a tiempo")
                                            return@launch
                                        }

                                        // 2️⃣ Crear la ruta usando los datos obtenidos
                                        routeViewModel.createRoute(
                                            routeName,
                                            price.toDouble(),
                                            directionsResult?.duration ?: "",
                                            directionsResult?.distance ?: "",
                                            "Inactive",
                                            directionsResult?.polylinePoints?:"",
                                            profileId
                                        )

                                        // 3️⃣ Esperar un poco a que el _route se actualice
                                        delay(500) // breve espera para asegurar que route.value esté listo

                                        // 4️⃣ Crear las relaciones Stop-Route
                                        selectedStops.forEach { stop ->
                                            routeViewModel.createStopRoute(route?.id ?: 0, stop.id)
                                        }
                                        showDialog = false
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Create", fontSize = 16.sp)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            TextButton(
                                onClick = { showDialog = false
                                    price = ""
                                    routeName = ""//onNavigateBack()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text("Cancel", fontSize = 16.sp, color = Color.Gray)
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StopSelectItem(stop: Stop, isSelected: Boolean, onCheckedChange: (Boolean)->Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6366F1).copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(stop.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(stop.address, fontSize = 12.sp, color = Color.Gray)
        }

        Checkbox(
            checked = isSelected,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF6366F1),
                uncheckedColor = Color.Gray
            )
        )
    }
}
