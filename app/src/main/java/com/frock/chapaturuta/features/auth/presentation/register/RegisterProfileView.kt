package com.frock.chapaturuta.features.auth.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frock.chapaturuta.features.auth.presentation.login.PrimaryColor
import com.frock.chapaturuta.features.auth.presentation.login.BackgroundColor

@Composable
fun RegisterProfileView(
    onRegisterClick: (
        firstName: String,
        lastName: String,
        phone: String,
        email: String,
        model: String,
        plate: String,
        color: String
    ) -> Unit,
    onCancelClick: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

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
            // Logo
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_compass),
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp),
                tint = PrimaryColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Main Information
            Text(
                text = "Main Information",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f),
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
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
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
                    onRegisterClick(firstName, lastName, phone, email, model, plate, color)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterProfileViewPreview() {
    RegisterProfileView(
        onRegisterClick = { _, _, _, _, _, _, _ -> },
        onCancelClick = {}
    )
}
