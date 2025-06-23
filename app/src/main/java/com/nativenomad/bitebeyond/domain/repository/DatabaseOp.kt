package com.nativenomad.bitebeyond.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nativenomad.bitebeyond.models.Category
import com.nativenomad.bitebeyond.models.Restaurants
import kotlinx.coroutines.flow.Flow

interface DatabaseOp {
    suspend fun getCategories():Flow<List<Category>>
    suspend fun getRestaurants(): Flow<List<Restaurants>>
}