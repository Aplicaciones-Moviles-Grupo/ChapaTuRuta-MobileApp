package com.frock.chapaturuta.features.auth.presentation.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.frock.chapaturuta.R
import com.frock.chapaturuta.features.auth.presentation.login.PrimaryColor
import com.frock.chapaturuta.features.auth.presentation.login.BackgroundColor
import com.frock.chapaturuta.features.profile.presentation.ProfileUiState
import com.frock.chapaturuta.features.profile.presentation.ProfileViewModel

@Composable
fun RegisterProfileView(
    viewModel: ProfileViewModel = hiltViewModel(),
    userId: Int,
    onRegisterClick: (
        profileId:Int
    ) -> Unit,
    onCancelClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getProfileByUserId(userId)
    }

    when(uiState){
        is ProfileUiState.Loading->{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileUiState.Error -> {
            Text("Error: ${(uiState as ProfileUiState.Error).message}")
        }

        is ProfileUiState.Success ->{
            val profile = (uiState as ProfileUiState.Success).profile
            var firstName by remember { mutableStateOf(profile.firstName) }
            var lastName by remember { mutableStateOf(profile.lastName) }
            var phoneNumber by remember { mutableStateOf(profile.phoneNumber) }
            var email by remember { mutableStateOf(profile.email) }

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
                        text = "Create Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
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
                            AsyncImage(
                                model = profile.profileImageUrl,
                                contentDescription = "",
                                modifier = Modifier.height(96.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para abrir la galería
                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
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

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone (+51)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
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
                            viewModel.updateProfile(profile.id, newImageUri,
                                firstName,lastName,email,phoneNumber, "Driver")
                            onRegisterClick(profile.id)
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
        }

        else -> {}
    }


}

