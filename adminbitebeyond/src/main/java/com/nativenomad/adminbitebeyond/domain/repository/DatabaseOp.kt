package com.nativenomad.adminbitebeyond.domain.repository

import com.nativenomad.adminbitebeyond.models.Menu
import com.nativenomad.adminbitebeyond.models.Offers
import com.nativenomad.adminbitebeyond.models.Order
import com.nativenomad.adminbitebeyond.models.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseOp {
    suspend fun saveRestaurantData(restaurantEntity: RestaurantEntity)
    suspend fun addMenuItem(menuItem: Menu)
    suspend fun removeMenuItem(menuItem:Menu)
    suspend fun getAllOffers(): Flow<List<Offers>>
    suspend fun getMyOffers():Flow<List<Offers>>
    suspend fun addOffer(offer: Offers)
    suspend fun deleteOffer(offer: Offers)
    suspend fun saveCategoriesGlobally(menuItem: Menu)
    suspend fun getFullMenu():Flow<List<Menu>>
    suspend fun getOrdersForRestaurant(): Flow<List<Order>>
    suspend fun updateOrderStatus(restaurantId: String,userId:String, orderId: String, newStatus: String)

}