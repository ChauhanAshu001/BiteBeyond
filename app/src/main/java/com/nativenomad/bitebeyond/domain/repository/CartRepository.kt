package com.nativenomad.bitebeyond.domain.repository

import com.nativenomad.bitebeyond.models.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow

interface CartRepository {
    val cartItems: MutableStateFlow<MutableMap<FoodItem, Int>>      //{FoodItem,Quantity}
    val total:MutableStateFlow<Int>
    val finalTotal:MutableStateFlow<Int>
    val discountAmount:MutableStateFlow<Int>

    suspend fun addToCart(food: FoodItem)
    suspend fun removeFromCart(food: FoodItem)
    suspend fun calculateTotalWithoutDiscount(): Int
    suspend fun calculateFinalTotal(): Int
    suspend fun applyPromoCode(code:String,promoCodeRestaurantMap:Map<String,String>)
    suspend fun clearCart()

}