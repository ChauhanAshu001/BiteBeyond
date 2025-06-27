package com.nativenomad.bitebeyond.presentation.cart


import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.bitebeyond.domain.repository.CartRepository
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.models.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val databaseOpUseCases: DatabaseOpUseCases
):ViewModel() {
//    private val _cartItems = MutableStateFlow<MutableMap<FoodItem, Int>>(mutableMapOf())
    private val _cartItems = cartRepository.cartItems
    val cartItems=_cartItems.asStateFlow()


    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _promoCode=MutableStateFlow("")
    val promoCode=_promoCode.asStateFlow()

    private val _discountAmount=cartRepository.discountAmount
    val discountAmount=_discountAmount.asStateFlow()

    private val promoCodeRestaurantMap = MutableStateFlow<Map<String, String>>(emptyMap())

    private val _total=cartRepository.total
    val total=_total.asStateFlow()

    private val _finalTotal=cartRepository.finalTotal
    val finalTotal=_finalTotal.asStateFlow()

    val _promoApplied = MutableStateFlow(false)
    val promoApplied=_promoApplied.asStateFlow()

    val _addressChanged=MutableStateFlow(false)
    val addressChanged=_addressChanged.asStateFlow()


    init{
        fetchPromoCodeMap()

    }
    private fun fetchPromoCodeMap() {
        viewModelScope.launch {
            databaseOpUseCases.getPromoCodeRestaurantMap().collect { map ->
                promoCodeRestaurantMap.value = map
            }
        }
    }

    fun onEvent(event: CartEvents, food:FoodItem) {
        when (event) {
            is CartEvents.PlusClicked -> {
                viewModelScope.launch {
                    cartRepository.addToCart(food)

                }
            }

            is CartEvents.MinusClicked -> {
                viewModelScope.launch {
                    cartRepository.removeFromCart(food)

                }
            }

        }
    }

    fun setAddress(address: String) {
        _address.value = address

    }
    fun saveAddress(yes:Boolean){
        if(yes){
            _addressChanged.value=true
        }
        else {
            _addressChanged.value=false  //while editing the outlined text field shouldn't be green
        }

    }
    fun setPromoCode(promoCode:String){
        _promoCode.value=promoCode
        _promoApplied.value=false  //while editing the outlined text field shouldn't be green
    }
    fun applyPromoCode(){
        viewModelScope.launch {
            cartRepository.applyPromoCode(code=_promoCode.value,promoCodeRestaurantMap=promoCodeRestaurantMap.value)
        }
        _promoApplied.value=true

    }

}