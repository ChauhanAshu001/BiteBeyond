package com.nativenomad.adminbitebeyond.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nativenomad.adminbitebeyond.utils.Constants

@Entity(tableName = "restaurant_table")
data class RestaurantEntity(
    @PrimaryKey val id: String = "admin_restaurant", /* Fixed ID because if you autogenerate then every time a new row is saved because
    id is incremented every time in saveRestaurant() function in RestaurantDataRepoImpl, when you create an entity
     val entity = RestaurantEntity(
            id = "admin_restaurant",
            restaurantName = _restaurantName.value,
            imageUrl = _imageUrl.value,
            restaurantDescription = _restaurantDescription.value,
            address = _address.value,
            pincode = _pincode.value,
            state = _state.value,
            country = _country.value,
            latitude = _lat.value,
            longitude = _longitude.value
        )
      and in profile it will always show first row's data because in Dao you're
     doing this
      @Query("SELECT * FROM restaurant_table LIMIT 1") */
    val uid: String,
    val restaurantName: String,
    val imageUrl: String,
    val restaurantDescription: String,
    val address: String,
    val pincode: String,
    val state: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
