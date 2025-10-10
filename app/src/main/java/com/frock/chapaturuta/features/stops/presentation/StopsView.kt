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
import com.frock.chapaturuta.core.ui.components.StopCard
import com.frock.chapaturuta.R

@Composable
fun StopsView(
    onNavigateToCreateStop: () -> Unit = {},
    onNavigateToEditStop: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    // Mock data
    val stops = listOf(
        Stop("Stop Name #01", "Av. Javier Prado 123"),
        Stop("Stop Name #02", "Av. Javier Prado 123")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Stops",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search a Stop") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Create Stop button
        Button(
            onClick = onNavigateToCreateStop,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create Stop", fontSize = 16.sp)
        }

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

        // Stops list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(stops) { stop ->
                StopCard(
                    stopName = stop.name,
                    address = stop.address,
                    onDelete = { },
                    onEdit = { onNavigateToEditStop(stop.name) }
                )
            }
        }
    }
}

data class Stop(val name: String, val address: String)

@Preview(showBackground = true)
@Composable
fun StopsViewPreview() {
    StopsView()
}