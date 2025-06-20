package com.nativenomad.bitebeyond.domain.usecases.login

import android.app.Activity
import androidx.activity.ComponentActivity
import com.facebook.CallbackManager
import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

class SignInWithFacebook(private val authManager: AuthManager) {
    operator fun invoke(activity: ComponentActivity, callbackManager: CallbackManager): Flow<AuthResponse> {
        return authManager.signInWithFacebook(activity, callbackManager)
    }
}