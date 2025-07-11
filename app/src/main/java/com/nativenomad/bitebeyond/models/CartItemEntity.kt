package com.nativenomad.bitebeyond.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// in cart i needed quantity so i created this data class
@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cost: String,
    val imageUrl: String,
//    val restaurantName: String,
    val restaurantUid:String,
    val quantity: Int
)
