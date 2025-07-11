package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Offers

class AddOffer(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(offers: Offers){
        databaseOp.addOffer(offers)
    }
}