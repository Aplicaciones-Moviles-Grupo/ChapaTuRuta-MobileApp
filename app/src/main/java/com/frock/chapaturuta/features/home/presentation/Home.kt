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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frock.chapaturuta.core.ui.components.RouteCard
import com.frock.chapaturuta.core.ui.components.StopCard
import com.frock.chapaturuta.core.ui.components.VehicleCard
import com.frock.chapaturuta.core.ui.theme.AppTheme

@Composable
fun Home(){

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(modifier = Modifier.height(64.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center){
                Icon(Icons.Default.Person,
                    contentDescription = "photo-profile",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Column(modifier = Modifier.weight(1f),verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Hello, Rodrigo Mendoza",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Good Morning!",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
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

        VehicleCard()

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

        RouteCard()
        RouteCard()

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

        StopCard()
        StopCard()
    }
}

@Composable
@Preview
fun HomePreview(){
    AppTheme(dynamicColor = false) {
        Home()
    }
}