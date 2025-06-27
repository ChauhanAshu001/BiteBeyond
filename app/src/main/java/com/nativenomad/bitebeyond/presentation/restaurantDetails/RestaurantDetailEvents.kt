package com.nativenomad.bitebeyond.presentation.restaurantDetails

sealed class RestaurantDetailEvents {
    object PlusClicked:RestaurantDetailEvents()
    object MinusClicked:RestaurantDetailEvents()
}