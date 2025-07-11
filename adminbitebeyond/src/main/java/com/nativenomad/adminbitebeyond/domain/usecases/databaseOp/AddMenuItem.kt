package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Menu

class AddMenuItem(private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(menu: Menu){
        databaseOp.addMenuItem(menu)
    }
}