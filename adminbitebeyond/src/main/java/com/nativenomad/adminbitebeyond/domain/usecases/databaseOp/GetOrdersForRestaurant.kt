package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Offers
import com.nativenomad.adminbitebeyond.models.Order
import kotlinx.coroutines.flow.Flow

class GetOrdersForRestaurant(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(): Flow<List<Order>> {
        return databaseOp.getOrdersForRestaurant()
    }
}