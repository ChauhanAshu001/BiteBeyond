package com.nativenomad.bitebeyond.data.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.nativenomad.bitebeyond.domain.repository.CartRepository
import com.nativenomad.bitebeyond.models.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow

class CartRepositoryImpl(
    private val application: Application
) :CartRepository {

    private val _cartItems = MutableStateFlow<MutableMap<FoodItem, Int>>(mutableMapOf())
    override val cartItems = _cartItems

    private val _total = MutableStateFlow(0)
    override val total = _total

    private val _finalTotal = MutableStateFlow(0)
    override val finalTotal = _finalTotal

    private val _discountAmount = MutableStateFlow(0)
    override val discountAmount = _discountAmount



    override suspend fun addToCart(food: FoodItem) {
        val currentCartItems = _cartItems.value.toMutableMap()
        currentCartItems[food] = (currentCartItems[food] ?: 0) + 1
        _cartItems.value = currentCartItems


        calculateTotalWithoutDiscount()
        calculateFinalTotal()

        Toast.makeText(application,"One item added to cart", Toast.LENGTH_SHORT).show()
    }

    override suspend fun removeFromCart(food: FoodItem) {
        val currentCartItems = _cartItems.value.toMutableMap()  //doing this is necessary to ensure map reference changes otherwise recomposition won't happen
        val currentQty = currentCartItems[food] ?: return
        if (currentQty > 1) {
            currentCartItems[food] = currentQty - 1
        } else {
            currentCartItems.remove(food)
        }
        _cartItems.value = currentCartItems

        calculateTotalWithoutDiscount()
        calculateFinalTotal()

        Toast.makeText(application,"One item removed from cart",Toast.LENGTH_SHORT).show()
    }

    override suspend fun calculateTotalWithoutDiscount(): Int {
        var sum=0
        for ((item, qty) in _cartItems.value) {
            val cleanedCost = item.cost.replace("\\D".toRegex(), "") //replaces anything that's not a digit (like rupee symbol and comma )to empty string
            sum += cleanedCost.toIntOrNull()?.times(qty) ?: 0
        }
        _total.value=sum

        return sum
    }

    override suspend fun calculateFinalTotal(): Int {
        _finalTotal.value=calculateTotalWithoutDiscount() - _discountAmount.value

        return _finalTotal.value
    }

    override suspend fun applyPromoCode(code: String, promoCodeRestaurantMap: Map<String, String>) {

        val promoCode=code.trim().uppercase()
        val targetRestaurant = promoCodeRestaurantMap[promoCode]

        var hasEligibleItem = false
        var restaurantSpecificTotal=0  //promocode should be applicable only on order total from that restaurant only for which promoCode is valid
        for ( (item,qty ) in cartItems.value.entries.toList()) {
            if (item.restaurantName == targetRestaurant) {
                val cleanedCost = item.cost.replace("\\D".toRegex(), "") //replaces anything that's not a digit (like rupee symbol and comma )to empty string
                restaurantSpecificTotal += cleanedCost.toIntOrNull()?.times(qty) ?: 0
                hasEligibleItem = true

            }
        }

        discountAmount.value = if (!hasEligibleItem) {
            0
        } else {
            when (promoCode) {
                "100OFF" -> if (restaurantSpecificTotal > 300) 100 else 0
                "SUGARLOVER" -> if (restaurantSpecificTotal > 400) 50 else 0
                "DAKSHIN50" -> if (restaurantSpecificTotal > 499) 50 else 0
                "JWELITE" -> if (restaurantSpecificTotal > 999) 100 else 0
                "KABAB20" -> (restaurantSpecificTotal / 250) * 20
                "KATHIROLL30" -> if (restaurantSpecificTotal > 300) 30 else 0
                "DELUXE50" -> if (restaurantSpecificTotal > 599) 50 else 0
                "CHHOLEPARTY" -> if (restaurantSpecificTotal > 300) 50 else 0
                "300PE150" -> if (restaurantSpecificTotal > 300) 150 else 0
                else -> 0
            }
        }

        calculateTotalWithoutDiscount()
        calculateFinalTotal()
    }


}