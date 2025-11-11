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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.frock.chapaturuta.R
import com.frock.chapaturuta.features.stops.domain.models.Stop

@Composable
fun StopCard(stop: Stop,
             onDelete: () -> Unit = {},
             onEdit: () -> Unit = {},
             onSelect: ()-> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(80.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        onClick = onSelect
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            AsyncImage(
                model = stop.stopImageUrl,
                contentDescription = "",
                modifier = Modifier.height(96.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp, 0.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stop.name, style = MaterialTheme.typography.titleMedium)
                Text(text = stop.address, style = MaterialTheme.typography.labelSmall)
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Red)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
                }

                /*Spacer(modifier = Modifier.padding(4.dp, 0.dp))
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                }*/
            }
        }
    }
}

@Composable
@Preview
fun StopCardPreview() {
    //StopCard()
}