package com.nativenomad.bitebeyond.domain.usecases.login

import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

class CreateAccountWithEmail(private val authManager: AuthManager) {
    operator fun invoke(email:String,password:String): Flow<AuthResponse> {
        return authManager.createAccountWithEmail(email=email,password=password)
    }
}