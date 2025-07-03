package com.nativenomad.adminbitebeyond.domain.usecases.login


import androidx.activity.ComponentActivity
import com.facebook.CallbackManager
import com.nativenomad.adminbitebeyond.domain.manager.AuthManager
import com.nativenomad.adminbitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

class SignInWithFacebook(private val authManager: AuthManager) {
    operator fun invoke(activity: ComponentActivity, callbackManager: CallbackManager): Flow<AuthResponse> {
        return authManager.signInWithFacebook(activity, callbackManager)
    }
}