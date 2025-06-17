package com.nativenomad.bitebeyond.domain.usecases.login

import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

class SignInWithGoogle(private val authManager: AuthManager) {
    operator fun invoke(): Flow<AuthResponse> {
        return authManager.signInWithGoogle()
    }
}