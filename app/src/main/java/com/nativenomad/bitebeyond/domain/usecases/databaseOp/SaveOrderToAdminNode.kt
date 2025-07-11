package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Order

class SaveOrderToAdminNode  (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(restaurantId: String, orderId: String, order: Order) {
        databaseOp.saveOrderToAdminNode(restaurantId=restaurantId,orderId=orderId,order=order)
    }
}