package com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo

sealed class RestaurantInfoNavigationEvent {
    object NavigateToLocationInfoScreen: RestaurantInfoNavigationEvent()
}