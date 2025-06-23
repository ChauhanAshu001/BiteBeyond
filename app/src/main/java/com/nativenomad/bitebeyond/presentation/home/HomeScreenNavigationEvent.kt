package com.nativenomad.bitebeyond.presentation.home

sealed class HomeScreenNavigationEvent {

    object navigateToCartScreen:HomeScreenNavigationEvent()
    object navigateTobotScreen:HomeScreenNavigationEvent()
    object navigateToProfileScreen:HomeScreenNavigationEvent()
    object navigateToHomeScreen:HomeScreenNavigationEvent()
    object navigateToSearchScreen:HomeScreenNavigationEvent()
    object navigateToRestaurantDetailsScreen:HomeScreenNavigationEvent()
    object navigateToCategoryDetailsScreen:HomeScreenNavigationEvent()


}