package com.nativenomad.adminbitebeyond.domain.repository

import com.nativenomad.adminbitebeyond.models.Menu
import kotlinx.coroutines.flow.MutableStateFlow

interface RestaurantMenuRepo {
    val menu:MutableStateFlow<List<Menu>>

    suspend fun addItem(menuItem:Menu)
    suspend fun removeItem(menuItem:Menu)
    suspend fun addCategoryGlobally(menuItem: Menu)
}