package com.example.city.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.city.data.model.Place

@Composable
fun PlaceListCard(place: Place, onClick: ()-> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = place.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun PlaceDetailScreen(placeId: String, viewModel: PlaceViewModel= viewModel()) {
    val places by viewModel.places.collectAsState()
    val place = places.find { it.id == placeId }

    place?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            // Load full image from assets
            val context = LocalContext.current
            val bitmap = remember(place.image) {
                val inputStream = context.assets.open(place.image)
                BitmapFactory.decodeStream(inputStream).asImageBitmap()
            }

            Image(
                bitmap = bitmap,
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(8.dp))
            Text(text = place.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = place.description)
        }
    }
}
