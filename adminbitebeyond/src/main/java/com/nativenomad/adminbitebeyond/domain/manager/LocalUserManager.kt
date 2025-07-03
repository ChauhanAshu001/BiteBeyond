package com.nativenomad.adminbitebeyond.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry()      //only "saving of data" is run on a coroutine in datastore library
    fun readAppEntry(): Flow<Boolean>      //"reading of data" don't run on coroutine
}