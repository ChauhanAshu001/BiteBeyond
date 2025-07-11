package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Order

class SaveOrderToUserNode (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(userId: String, orderId: String, order: Order) {
        databaseOp.saveOrderToUserNode(userId=userId,orderId=orderId,order=order)
    }
}