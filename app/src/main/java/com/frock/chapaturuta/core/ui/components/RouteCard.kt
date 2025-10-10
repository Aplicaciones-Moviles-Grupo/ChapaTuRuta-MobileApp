package com.frock.chapaturuta.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frock.chapaturuta.R
import com.frock.chapaturuta.core.ui.theme.AppTheme

@Composable
fun RouteCard(isActive: Boolean){
    Card(modifier = Modifier.padding(8.dp)
        .height(80.dp)
        .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(painterResource(R.drawable.imagetemplate),
                contentDescription = null,
                contentScale = ContentScale.FillHeight)

            Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(8.dp,0.dp), verticalArrangement = Arrangement.Center) {
                Text(text = "Ruta L-M-V", style = MaterialTheme.typography.titleMedium)
                Text(text = "Stop 1 -> Stop 2 -> Stop 3", style = MaterialTheme.typography.labelSmall)
            }

            Column(modifier = Modifier.fillMaxHeight().padding(8.dp,0.dp), verticalArrangement = Arrangement.Center) {

                if(isActive){
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Green, contentColor = Color.White)) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null)
                        Spacer(modifier = Modifier.padding(4.dp,0.dp))
                        Text("Active")
                    }
                }else{
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White)) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null)
                        Spacer(modifier = Modifier.padding(4.dp,0.dp))
                        Text("Disabled")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun RouteCardPreview(){
    AppTheme(dynamicColor = false) {
        RouteCard(false)
    }
}
