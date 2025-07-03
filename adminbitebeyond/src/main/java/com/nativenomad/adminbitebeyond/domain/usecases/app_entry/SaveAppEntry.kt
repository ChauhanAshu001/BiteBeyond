package com.nativenomad.adminbitebeyond.domain.usecases.app_entry

import com.nativenomad.adminbitebeyond.domain.manager.LocalUserManager

class SaveAppEntry (private val localUserManager: LocalUserManager){
    //operator function means we can call this function by the name of its class
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}
