package com.nativenomad.bitebeyond.domain.usecases.databaseOp

import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Restaurants
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.flow.Flow

class SaveUserData (private val databaseOp: DatabaseOp) {
    suspend operator fun invoke(userId: String, user: UserProfile) {
        return databaseOp.saveUserData(userId,user)
    }
}