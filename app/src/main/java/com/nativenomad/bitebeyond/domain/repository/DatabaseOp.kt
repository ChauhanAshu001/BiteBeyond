package com.nativenomad.bitebeyond.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nativenomad.bitebeyond.models.Category
import com.nativenomad.bitebeyond.models.FoodItem
import com.nativenomad.bitebeyond.models.Offers
import com.nativenomad.bitebeyond.models.Restaurants
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface DatabaseOp {
    suspend fun getCategories():Flow<List<Category>>
    suspend fun getRestaurants(): Flow<List<Restaurants>>
    suspend fun getMenu(restaurantName: String):Flow<List<FoodItem>>
    suspend fun getOffers(restaurantName: String):Flow<List<Offers>>
    suspend fun getPromoCodeRestaurantMap():Flow<MutableMap<String,String>>
    suspend fun saveUserData(userId: String, user: UserProfile)
    suspend fun getUserData(userId: String): Flow<UserProfile?>
}