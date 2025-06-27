package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Offers
import kotlinx.coroutines.flow.Flow

class GetPromoCodeRestaurantMap (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(): Flow<MutableMap<String, String>> {
        return databaseOp.getPromoCodeRestaurantMap()
    }
}