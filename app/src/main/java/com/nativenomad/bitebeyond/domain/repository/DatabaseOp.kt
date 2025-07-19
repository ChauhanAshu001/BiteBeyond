package com.nativenomad.bitebeyond.domain.repository


import com.nativenomad.bitebeyond.models.Category
import com.nativenomad.bitebeyond.models.FoodItem
import com.nativenomad.bitebeyond.models.Offers
import com.nativenomad.bitebeyond.models.Order
import com.nativenomad.bitebeyond.models.Restaurants
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface DatabaseOp {
    suspend fun getCategories():Flow<List<Category>>
    suspend fun getRestaurants(): Flow<List<Restaurants>>
    suspend fun getMenu(restaurantUid: String):Flow<List<FoodItem>>
    suspend fun getOffers(restaurantUid: String):Flow<List<Offers>>
    suspend fun getPromoCodeRestaurantMap():Flow<MutableMap<String,String>>
    suspend fun saveUserData(userId: String, user: UserProfile)
    suspend fun getUserData(userId: String): Flow<UserProfile?>
    suspend fun getNewOrderIdForUser(userId: String): String
    suspend fun saveOrderToUserNode(userId: String, orderId: String, order: Order)
    suspend fun saveOrderToAdminNode(restaurantId: String, orderId: String, order: Order)
    suspend fun getUserOrders():Flow<List<Order>>
}