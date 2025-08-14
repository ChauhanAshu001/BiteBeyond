package com.nativenomad.bitebeyond.presentation.profile.allOrders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.models.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PastOrderViewModel@Inject constructor(
    private val databaseOpUseCases: DatabaseOpUseCases
):ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    fun fetchOrders() {

        viewModelScope.launch {
            databaseOpUseCases.getUserOrders().collect {allOrders->
                _orders.value = allOrders.sortedByDescending { it.timestamp }
            }
        }
    }
}