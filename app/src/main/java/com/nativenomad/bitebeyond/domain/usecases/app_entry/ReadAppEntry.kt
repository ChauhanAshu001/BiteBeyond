package com.nativenomad.bitebeyond.domain.usecases.app_entry

import com.nativenomad.bitebeyond.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (private val localUserManager: LocalUserManager){
    //operator function means we can call this function by the name of its class
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}
