package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.FoodItem
import kotlinx.coroutines.flow.Flow

class GetNewOrderIdForUser (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(userId: String):String {
        return databaseOp.getNewOrderIdForUser(userId)
    }
}