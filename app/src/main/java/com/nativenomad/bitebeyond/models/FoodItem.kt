package com.nativenomad.bitebeyond.models

data class FoodItem(
    val name:String="",
    val cost:String="",
    val imageUrl:String="",
    val restaurantName:String="" //this will be used in promo code implementation(see cart view model, there it's used)
)