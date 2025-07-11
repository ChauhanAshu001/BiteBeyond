package com.nativenomad.bitebeyond.presentation.restaurantDetails

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.bitebeyond.domain.repository.CartRepository
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.models.FoodItem
import com.nativenomad.bitebeyond.models.Offers
import com.nativenomad.bitebeyond.presentation.cart.CartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel@Inject constructor(
    private val databaseOpUseCases: DatabaseOpUseCases,
    private val cartRepository: CartRepository

):ViewModel() {
    private val _menuItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val menuItems = _menuItems.asStateFlow()

    private val _offers = MutableStateFlow<List<Offers>>(emptyList())
    val offers = _offers.asStateFlow()


    //I didn't load menu when i was just loading restaurants for homeScreen because if menu is large I didn't wanted app to become so slow that even home screen loading takes time
    fun loadMenu(restaurantUid: String) {
        viewModelScope.launch {
            databaseOpUseCases.getMenu(restaurantUid).collect { it ->
                _menuItems.value = it
            }
        }
    }

    fun loadOffers(restaurantUid: String) {
        viewModelScope.launch {
            databaseOpUseCases.getOffers(restaurantUid).collect { it ->
                _offers.value = it
            }
        }
    }

    fun onEvent(event: RestaurantDetailEvents,food:FoodItem) {
        when (event) {
            is RestaurantDetailEvents.PlusClicked -> {
                viewModelScope.launch {
                    cartRepository.addToCart(food)
                }
            }

            is RestaurantDetailEvents.MinusClicked -> {
                viewModelScope.launch {
                    cartRepository.removeFromCart(food)
                }
            }
        }
    }
}