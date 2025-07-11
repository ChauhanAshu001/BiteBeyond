package com.nativenomad.adminbitebeyond.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nativenomad.adminbitebeyond.models.RestaurantEntity

@Database(entities = [RestaurantEntity::class], version = 1)
abstract class RestaurantDatabase: RoomDatabase() {
    abstract fun restaurantDao():RestaurantDao
}