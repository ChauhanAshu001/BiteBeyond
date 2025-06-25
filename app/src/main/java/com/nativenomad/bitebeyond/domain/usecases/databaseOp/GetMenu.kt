package com.nativenomad.bitebeyond.domain.usecases.databaseOp


import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.FoodItem
import kotlinx.coroutines.flow.Flow

class GetMenu (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(restaurantName:String): Flow<List<FoodItem>> {
        return databaseOp.getMenu(restaurantName)
    }
}