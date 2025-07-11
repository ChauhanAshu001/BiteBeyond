package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Menu
import kotlinx.coroutines.flow.Flow

class GetFullMenu (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(): Flow<List<Menu>> {
        return databaseOp.getFullMenu()
    }
}