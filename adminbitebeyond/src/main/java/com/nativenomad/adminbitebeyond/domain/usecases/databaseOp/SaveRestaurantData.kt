package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.RestaurantEntity

class SaveRestaurantData(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(restaurantEntity: RestaurantEntity){
        databaseOp.saveRestaurantData(restaurantEntity)
    }
}