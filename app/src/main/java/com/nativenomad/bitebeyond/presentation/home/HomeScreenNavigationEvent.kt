package com.nativenomad.bitebeyond.presentation.home

sealed class HomeScreenNavigationEvent {


//    object navigateToSearchScreen:HomeScreenNavigationEvent()
    data class navigateToRestaurantDetailsScreen(
    val restaurantName:String,
    val distanceKm: String,
    val imageUrl: String,
    val rating:Double):HomeScreenNavigationEvent()
//    object navigateToCategoryDetailsScreen:HomeScreenNavigationEvent()


}