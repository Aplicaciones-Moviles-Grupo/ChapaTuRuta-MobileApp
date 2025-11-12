package com.frock.chapaturuta.features.routes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frock.chapaturuta.R

@Composable
fun EditRouteView(
    routeId: String,
    onNavigateBack: () -> Unit = {}
) {
    var routeName by remember { mutableStateOf("Route Name #01") }
    /*val stops = remember {
        mutableStateListOf(
            StopItem("Stop Name #01", "Av. Javier Prado 123", true),
            StopItem("Stop Name #02", "Av. Javier Prado 123", true),
            StopItem("Stop Name #03", "Av. Javier Prado 123", false)
        )
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Edit Route",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

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

        Text(
            text = "Add/Remove stops",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Map placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.map_placeholder),
                contentDescription = "Map",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        // Buttons
        Button(
            onClick = { /* Update logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Update Route", fontSize = 16.sp)
        }

        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Cancel", fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditRouteViewPreview() {
    EditRouteView(routeId = "1")
}