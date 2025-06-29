package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.flow.Flow

class GetUserData (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(userId: String): Flow<UserProfile?> {
        return databaseOp.getUserData(userId)
    }
}