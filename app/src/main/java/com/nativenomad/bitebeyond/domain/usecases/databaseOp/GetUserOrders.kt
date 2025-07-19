package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Order
import kotlinx.coroutines.flow.Flow

class GetUserOrders(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(): Flow<List<Order>> {
        return databaseOp.getUserOrders()
    }
}