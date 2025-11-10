package com.frock.chapaturuta.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.frock.chapaturuta.R
import com.frock.chapaturuta.core.ui.theme.AppTheme
import com.frock.chapaturuta.features.profile.domain.models.Vehicle

@Composable
fun VehicleCard(vehicle: Vehicle?){

    Card(modifier = Modifier.padding(8.dp)
        .height(128.dp)
        .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))) {
        Row(modifier = Modifier.fillMaxHeight()) {
            AsyncImage(model = vehicle?.vehicleImageUrl ?: "",
                contentDescription = null,
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillHeight)
            Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(8.dp,0.dp), verticalArrangement = Arrangement.Center) {
                Text(text = "Model:  ${vehicle?.model?:""}")
                Text(text = "Plate:  ${vehicle?.plate?:""}")
                Text(text = "Color: ${vehicle?.color?:""}")
            }
        }
    }
}

@Composable
@Preview
fun VehicleCardPreview(){
    AppTheme(dynamicColor = false) {
        //VehicleCard()
    }
}