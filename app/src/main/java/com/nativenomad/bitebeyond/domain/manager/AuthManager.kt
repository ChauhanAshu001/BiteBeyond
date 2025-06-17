package com.nativenomad.bitebeyond.domain.manager

import com.nativenomad.bitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthManager {
    fun createAccountWithEmail(email:String,password:String): Flow<AuthResponse>
    fun loginWithEmail(email:String,password: String):Flow<AuthResponse>

    //google signIn
    fun signInWithGoogle():Flow<AuthResponse>
}