package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Offers

class DeleteOffer(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(offers: Offers){
        databaseOp.deleteOffer(offers)
    }
}