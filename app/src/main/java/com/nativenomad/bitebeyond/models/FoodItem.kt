package com.nativenomad.bitebeyond.models

// in restaurant detail screen i needed to show these details regarding each food so i created this data class
data class FoodItem(
    val name:String="",
    val cost:String="",
    val imageUrl:String="",
    val restaurantUid:String="", //this will be used in promo code implementation(see cart view model, there it's used)
    val foodCategory:String=""
)