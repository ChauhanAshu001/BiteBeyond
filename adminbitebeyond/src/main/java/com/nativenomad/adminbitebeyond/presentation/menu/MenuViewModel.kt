package com.nativenomad.adminbitebeyond.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantMenuRepo
import com.nativenomad.adminbitebeyond.models.Menu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel@Inject constructor(
    private val restaurantMenuRepo: RestaurantMenuRepo
):ViewModel() {


    private val _menu= restaurantMenuRepo.menu
    val menu=_menu.asStateFlow()


    fun deleteItem(foodItem: Menu){
        viewModelScope.launch {
            restaurantMenuRepo.removeItem(foodItem)
        }
    }
}