package com.frock.chapaturuta.features.stops.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.frock.chapaturuta.R
import com.frock.chapaturuta.features.stops.domain.models.Stop
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun CreateStopView(
    onNavigateBack: () -> Unit = {},
    profileId: Int,
    viewModel: StopViewModel = hiltViewModel()
) {

    viewModel.getAllStops(profileId)

    val stops by viewModel.stops.collectAsState()


    val address1 by viewModel.address.collectAsState()

    // Estado del formulario
    var showDialog by remember { mutableStateOf(false) }
    var tempLatLng by remember { mutableStateOf<LatLng?>(null) }

    // Campos del formulario
    var stopName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Cámara inicial
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }

    // Imagen nueva seleccionada (local)
    var newImageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador de la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { newImageUri = it }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Create Stop",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {

            // Mapa
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    // Guardamos la posición temporal y abrimos el formulario
                    tempLatLng = latLng
                    showDialog = true
                    viewModel.getAddressFromCoordinates(latLng.latitude, latLng.longitude)
                }
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

            LaunchedEffect(address1) {
                if (showDialog && address1.isNotEmpty()) {
                    address = address1
                }
            }

            // Dialog para registrar un nuevo paradero
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            tempLatLng?.let { latLng ->
                                viewModel.createStop(newImageUri, stopName, address, latLng.latitude, latLng.longitude, profileId)

                            }
                            // Limpiamos y cerramos
                            stopName = ""
                            address = ""
                            tempLatLng = null
                            showDialog = false
                            newImageUri= null
                        }) {
                            Text("Crear Paradero", textAlign = TextAlign.Center)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            stopName = ""
                            address = ""
                            tempLatLng = null
                            showDialog = false
                        }) {
                            Text("Cancelar")
                        }
                    },
                    title = { Text("Nuevo Paradero", textAlign = TextAlign.Center) },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(140.dp)
                                    .background(Color.LightGray)
                            ) {
                                if(newImageUri != null){
                                    Image(
                                        painter = rememberAsyncImagePainter(newImageUri),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }else{
                                    Image(
                                        painterResource(R.drawable.imagetemplate),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Botón para abrir la galería
                            Button(
                                onClick = { launcher.launch("image/*") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                            ) {
                                Icon(Icons.Default.Upload, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Upload Image")
                            }

                            OutlinedTextField(
                                value = stopName,
                                onValueChange = { stopName = it },
                                label = { Text("Name") },
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("Address") }
                            )
                        }
                    }
                )
            }

            // Texto informativo
            if (!showDialog) {
                Text(
                    text = "Toca el mapa para agregar un paradero",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                )
            }
        }
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Done", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateStopViewPreview() {
    //CreateStopView()
}