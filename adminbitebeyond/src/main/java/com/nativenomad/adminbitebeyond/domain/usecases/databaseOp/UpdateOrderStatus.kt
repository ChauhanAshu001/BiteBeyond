package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp

class UpdateOrderStatus(private val databaseOp: DatabaseOp) {

    suspend operator fun invoke(restaurantId: String,userId:String, orderId: String, newStatus: String) {
        databaseOp.updateOrderStatus(restaurantId,userId, orderId, newStatus)
    }
}