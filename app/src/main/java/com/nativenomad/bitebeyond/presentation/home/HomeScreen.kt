package com.nativenomad.bitebeyond.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.home.components.CategoryCard
import com.nativenomad.bitebeyond.presentation.home.components.RestaurantCard
import com.nativenomad.bitebeyond.presentation.home.components.ShimmerCategoryCard
import com.nativenomad.bitebeyond.presentation.home.components.ShimmerRestaurantCard
import com.nativenomad.bitebeyond.presentation.login.SignUpEvent
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    //fetching location
    val context = LocalContext.current
    //You cannot request runtime permissions directly from a ViewModel — it's against Android's architecture principles.That's why this location permission logic is here in UI screen.
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // viewmodel will handle automatically
        } else {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        homeViewModel.fetchUserLocationAndName()

//        homeViewModel.navigateEvent.collectLatest { event ->
//            when (event) {
//                is HomeScreenNavigationEvent.navigateToRestaurantDetailsScreen -> {
//                    val encodedUrl = Uri.encode(event.imageUrl)
//                    navController.navigate("${Routes.RestaurantDetailScreen.route}/${event.restaurantName}/${event.distanceKm}/${encodedUrl}/${event.rating}")
//                }
//            }
//        }
    }

    val categories = homeViewModel.categories
    val featuredRestaurants = homeViewModel.restaurants
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    val uiState= homeViewModel.uiState.collectAsState()
    val locationName=homeViewModel.locationName.collectAsState()

    //navigation logic to details screen of restaurant

    LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 64.dp)

        ) {

            // Location and Header

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Adjust as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home_screen_background),
                        contentDescription = "Home Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Your Location",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.LightGray
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = Color(0xFFFF7B00)
                                    )
                                    Text(
                                        text = locationName.value,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                IconButton(onClick = {navController.navigate(Routes.SearchScreen.route) }) {
                                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                                }
                                IconButton(onClick = { /* Notifications */ }) {
                                    Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Get the best food for you",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )
                    }
                }
            }


            // Category Header
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Find by Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "See All",
                        color = Color(0xFFFF7B00),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { /* See All */ }
                    )
                }
            }

            // Category List
            item {
                if(uiState.value == SignUpEvent.Loading){
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(3) {
                            ShimmerCategoryCard()
                        }
                    }
                }
                else{
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(categories) { category ->
                            CategoryCard(
                                category = category,
                                selected = selectedCategory == category.name,
                                onClick = { selectedCategory = category.name
                                            val encodedCategory = Uri.encode(category.name)
                                            navController.navigate("${Routes.CategoryFoodScreen.route}/${encodedCategory}")
                                }
                            )
                        }
                    }
                }

            }

            // Featured Restaurants Header
            item {
                Text(
                    text = "Restaurants",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }

            // Restaurant Cards Grid (2-column)

            if(uiState.value == SignUpEvent.Loading){
                val shimmerList= List(6){it}.chunked(2)
                items(shimmerList.chunked(2)) { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach {
                            Box(modifier = Modifier.weight(1f)) {
                                ShimmerRestaurantCard()
                            }
                        }
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            else{
                items(featuredRestaurants.chunked(2)) { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach { restaurant ->
                            Box(modifier = Modifier.weight(1f)) {
                                RestaurantCard(restaurant = restaurant, onClick = {
                                    val encodedUrl = Uri.encode(restaurant.imageUrl)
                                    navController.navigate("${Routes.RestaurantDetailScreen.route}/${restaurant.name}/${restaurant.distance}/${encodedUrl}/${restaurant.rating}")})
                            }
                        }
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item{
                Spacer(modifier = Modifier.fillMaxWidth().height(60.dp))
            }

        }

    }


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreen() {
    BiteBeyondTheme {
        HomeScreen(navController = rememberNavController())
    }
}
