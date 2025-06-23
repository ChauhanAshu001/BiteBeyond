package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Category
import com.nativenomad.bitebeyond.models.Restaurants
import kotlinx.coroutines.flow.Flow

class GetRestaurants (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(): Flow<List<Restaurants>> {
        return databaseOp.getRestaurants()
    }
}