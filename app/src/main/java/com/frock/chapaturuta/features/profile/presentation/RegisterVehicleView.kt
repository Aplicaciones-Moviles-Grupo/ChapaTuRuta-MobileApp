package com.frock.chapaturuta.features.profile.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.frock.chapaturuta.R
import com.frock.chapaturuta.features.auth.presentation.login.BackgroundColor
import com.frock.chapaturuta.features.auth.presentation.login.LoginUiState
import com.frock.chapaturuta.features.auth.presentation.login.PrimaryColor
import com.frock.chapaturuta.features.profile.domain.models.Profile

@Composable
fun RegisterVehicleView(
    viewModel: ProfileViewModel = hiltViewModel(),
    profileId: Int,
    onRegisterClick: (
        userId:Int
    ) -> Unit,
    onCancelClick: () -> Unit
) {

    val vehicleUiState by viewModel.vehicleUiState.collectAsState()
    //val profileUiState by viewModel.uiState.collectAsState()

    var model by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

    // Imagen nueva seleccionada (local)
    var newImageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador de la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { newImageUri = it }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Image(
                painter = painterResource(id = R.drawable.chapaturuta_logo),
                contentDescription = "Logo de Chapa Tu Ruta",
                modifier = Modifier.size(120.dp)
            )*/

            Text(
                text = "Create Vehicle - ProfileId: ${profileId}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                        contentScale = ContentScale.FillHeight
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para abrir la galería
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Icon(Icons.Default.Upload, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Upload Image")
            }

            // Main Information
            Text(
                text = "Main Information",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Vehicle Information
            Text(
                text = "Vehicle Information",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = plate,
                onValueChange = { plate = it },
                label = { Text("Plate (P6L782)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Register
            Button(
                onClick = {
                    viewModel.createVehicle(
                        newImageUri,
                        plate,
                        model,
                        color,
                        profileId
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Register", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Cancel
            OutlinedButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Gray
                )
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
        }
    }

    when(vehicleUiState){
        is VehicleUiState.Loading->{
            CircularProgressIndicator()
        }

        is VehicleUiState.Error -> {
            Text("Error: ${(vehicleUiState as VehicleUiState.Error).message}")
        }

        is VehicleUiState.Success ->{

            onRegisterClick(profileId)
        }
        else -> {}
    }
}