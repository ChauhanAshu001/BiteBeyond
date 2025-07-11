package com.nativenomad.adminbitebeyond.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nativenomad.adminbitebeyond.models.RestaurantEntity


@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: RestaurantEntity)

    @Query("SELECT * FROM restaurant_table LIMIT 1")
    suspend fun getRestaurant(): RestaurantEntity?
}