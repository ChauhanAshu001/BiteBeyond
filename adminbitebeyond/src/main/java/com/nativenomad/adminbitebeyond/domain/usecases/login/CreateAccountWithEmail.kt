package com.nativenomad.adminbitebeyond.domain.usecases.login

import com.nativenomad.adminbitebeyond.domain.manager.AuthManager
import com.nativenomad.adminbitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

class CreateAccountWithEmail(private val authManager: AuthManager) {
    operator fun invoke(email:String,password:String): Flow<AuthResponse> {
        return authManager.createAccountWithEmail(email=email,password=password)
    }
}