package com.nativenomad.bitebeyond.presentation.categoryFood

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.bitebeyond.domain.repository.CartRepository
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.models.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFoodViewModel@Inject constructor(
    private val databaseOpUseCases: DatabaseOpUseCases,
    private val cartRepository: CartRepository
) : ViewModel()  {

    private val _categoryFoodList=MutableStateFlow<List<FoodItem>>(emptyList())
    val categoryFoodList=_categoryFoodList.asStateFlow()

    fun loadFoodItemsByCategory(category: String) {
        viewModelScope.launch {
            val finalItems = mutableListOf<FoodItem>()
            databaseOpUseCases.getRestaurants().collectLatest { restaurants ->
                finalItems.clear()

                restaurants.forEach { restaurant ->
                    launch {    /*if you don't launch a new coroutine scope for each restaurant,then the menu of first restaurant will only be fetched and then since
                    collect is suspending function the function's execution stop here only to observe any changes in menu of first restaurant and the control never reaches the line
                    finalItems.addAll(filtered)
                    */
                        databaseOpUseCases.getMenu(restaurant.uid).collect { menu ->
                            val filtered = menu.filter {
                                it.name.contains(category, ignoreCase = true)
                            }
                            finalItems.addAll(filtered)

                            // Update state after each restaurant's menu is processed
                            _categoryFoodList.value = finalItems.toList()

                        }
                    }
                }
            }
        }
    }


    fun onEvent(event: CategoryFoodScreenEvents,food:FoodItem){
        when(event){
            is CategoryFoodScreenEvents.onPlusClicked->{
                viewModelScope.launch {
                    cartRepository.addToCart(food)
                }
            }
            is CategoryFoodScreenEvents.onMinusClicked->{
                viewModelScope.launch {
                    cartRepository.removeFromCart(food)
                }
            }

        }
    }
}