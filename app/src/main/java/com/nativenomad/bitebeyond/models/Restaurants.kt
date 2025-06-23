package com.nativenomad.bitebeyond.models

data class Restaurants(
    val distance: String="",
    val featured: Boolean=false,
    val imageUrl: String="",
    val location: LocationPoints=LocationPoints(0.0,0.0),
    val name: String="",
    val rating: Double=0.0

)