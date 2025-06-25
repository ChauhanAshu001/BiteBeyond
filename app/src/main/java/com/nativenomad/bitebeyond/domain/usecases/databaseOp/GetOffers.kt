package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.FoodItem
import com.nativenomad.bitebeyond.models.Offers
import kotlinx.coroutines.flow.Flow

class GetOffers(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(restaurantName:String): Flow<List<Offers>> {
        return databaseOp.getOffers(restaurantName)
    }
}