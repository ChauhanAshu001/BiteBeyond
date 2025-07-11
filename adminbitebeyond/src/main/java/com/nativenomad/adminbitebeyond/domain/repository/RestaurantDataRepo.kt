package com.nativenomad.adminbitebeyond.domain.repository


import kotlinx.coroutines.flow.MutableStateFlow

interface RestaurantDataRepo {
    val pincode:MutableStateFlow<String>
    val country:MutableStateFlow<String>
    val state:MutableStateFlow<String>
    val address:MutableStateFlow<String>
    val restaurantName:MutableStateFlow<String>
    val imageUrl:MutableStateFlow<String>
    val restaurantDescription:MutableStateFlow<String>
    val lat:MutableStateFlow<Double>
    val longitude:MutableStateFlow<Double>

    suspend fun setPincode(pincode:String)
    suspend fun setCountry(country:String)
    suspend fun setState(state:String)
    suspend fun setAddress(address:String)
    suspend fun setRestaurantName(name:String)
    suspend fun setImageUrl(url:String)
    suspend fun setRestaurantDescription(description:String)
    suspend fun calculateLatLong()
    suspend fun saveRestaurantData()

}