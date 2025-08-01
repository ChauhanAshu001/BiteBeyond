package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Menu

class RemoveMenuItem (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(menuItem: Menu){
        databaseOp.removeMenuItem(menuItem)
    }
}