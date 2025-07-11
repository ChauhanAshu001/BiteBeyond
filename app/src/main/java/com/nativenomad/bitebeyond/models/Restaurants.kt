package com.nativenomad.bitebeyond.models

data class Restaurants(

//    val featured: Boolean=false,
    val restaurantName: String = "",
    val restaurantDescription: String = "",
    val address: String = "",
    val pincode: String = "",
    val state: String = "",
    val country: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUrl: String = "",
    val rating: Double = 0.0,
    val uid: String = "",
    val distance: String = "" // I'll calculate this client-side and override it


)