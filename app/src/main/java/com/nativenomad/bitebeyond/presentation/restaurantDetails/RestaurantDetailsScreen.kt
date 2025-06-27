package com.nativenomad.bitebeyond.presentation.restaurantDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nativenomad.bitebeyond.presentation.restaurantDetails.components.FoodItemCard
import com.nativenomad.bitebeyond.presentation.restaurantDetails.components.OfferCard

@Composable
fun RestaurantDetailsScreen(
    restaurantName:String,
    distanceKm: String,
    imageUrl: String,
    rating: Float,
    restaurantDetailViewModel: RestaurantDetailViewModel= hiltViewModel()
) {
    val menuItems = restaurantDetailViewModel.menuItems.collectAsState()
    val offers = restaurantDetailViewModel.offers.collectAsState()

    LaunchedEffect(restaurantName) {
        restaurantDetailViewModel.loadMenu(restaurantName)
        restaurantDetailViewModel.loadOffers(restaurantName)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Image with Distance
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Restaurant Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Row (
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color.Red)
                Text(text = "$distanceKm km", fontWeight = FontWeight.Bold)
            }
        }

        // Restaurant Info
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFF9800))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = rating.toString(), fontWeight = FontWeight.Bold)
            }
            Text(
                restaurantName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Order delicious food delivered fresh from the restaurant.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

        }

        Text(text = "Use these promo codes in cart",
            style = MaterialTheme.typography.titleMedium,
            color=Color.Black,
            modifier = Modifier.padding(start=10.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            item{
                LazyRow (
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ){
                    items(offers.value){offer->
                        OfferCard(offer)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            items(menuItems.value) { food ->
                FoodItemCard(food=food, onEvent = restaurantDetailViewModel::onEvent)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}