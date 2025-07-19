package com.nativenomad.adminbitebeyond.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.adminbitebeyond.models.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel@Inject constructor(
    private val databaseOpUseCases: DatabaseOpUseCases
):ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    private val _uiState= MutableStateFlow<HomeScreenEvents>(HomeScreenEvents.Nothing)
    val uiState=_uiState.asStateFlow()

    fun fetchOrders() {

        viewModelScope.launch {
            _uiState.value=HomeScreenEvents.Loading

            try{
                databaseOpUseCases.getOrdersForRestaurant().collect {allOrders->
                    _orders.value = allOrders.filter { it.status.lowercase() != "delivered" }
                        .sortedByDescending { it.timestamp }
                    _uiState.value=HomeScreenEvents.Success

                }
            }catch (e:Exception){
                _uiState.value=HomeScreenEvents.Error
            }

        }
    }

    fun onStatusUpdate(restaurantId: String,userId:String, orderId: String, newStatus: String){
        viewModelScope.launch {
            if(newStatus.lowercase()=="delivered"){
                delay(1500)  //to give a smooth visual experience i will make card disappear after 1.5 sec
            }
            databaseOpUseCases.updateOrderStatus(restaurantId,userId, orderId, newStatus)
        }
    }
}