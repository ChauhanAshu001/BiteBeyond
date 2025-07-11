package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Offers
import kotlinx.coroutines.flow.Flow

class GetMyOffers  (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(): Flow<List<Offers>> {
        return databaseOp.getMyOffers()
    }
}