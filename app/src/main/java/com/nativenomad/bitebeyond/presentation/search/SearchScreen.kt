package com.nativenomad.bitebeyond.presentation.search

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nativenomad.bitebeyond.presentation.home.HomeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.presentation.home.components.RestaurantCard
import com.nativenomad.bitebeyond.presentation.navgraph.Routes

@Composable
fun SearchScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    var searchText by remember { mutableStateOf("") }

    val restaurants = homeViewModel.restaurants
    val filteredRestaurants = restaurants.filter {
        it.name.contains(searchText, ignoreCase = true)
    }



    Column(modifier = Modifier.fillMaxSize().padding(start= 16.dp,top=90.dp,end=16.dp,bottom=70.dp)) {

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search restaurant") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredRestaurants) { restaurant ->
                RestaurantCard(
                    restaurant = restaurant,
                    onClick = {
                        val encodedUrl = Uri.encode(restaurant.imageUrl)
                        navController.navigate("${Routes.RestaurantDetailScreen.route}/${restaurant.name}/${restaurant.distance}/${encodedUrl}/${restaurant.rating}")
                    }
                )
            }

            if (filteredRestaurants.isEmpty()) {
                item {
                    Text(
                        text = "No results found.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}
