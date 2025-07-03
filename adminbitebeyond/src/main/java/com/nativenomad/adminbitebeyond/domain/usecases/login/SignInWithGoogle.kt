package com.nativenomad.adminbitebeyond.domain.usecases.login

import com.nativenomad.adminbitebeyond.domain.manager.AuthManager
import com.nativenomad.adminbitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

class SignInWithGoogle(private val authManager: AuthManager) {
    operator fun invoke(): Flow<AuthResponse> {
        return authManager.signInWithGoogle()
    }
}