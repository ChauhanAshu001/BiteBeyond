package com.nativenomad.adminbitebeyond.models

data class Restaurant(
    val offers: List<Offers>,
    val distance: String="",
    val imageUrl: String="",
    val location: Location,
    val menu: List<Menu>,
    val name: String="",
    val rating: Double=0.0
)