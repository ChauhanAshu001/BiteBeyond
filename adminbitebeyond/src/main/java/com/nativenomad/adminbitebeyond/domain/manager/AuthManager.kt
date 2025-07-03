package com.nativenomad.adminbitebeyond.domain.manager

import android.app.Activity
import androidx.activity.ComponentActivity
import com.facebook.CallbackManager
import com.nativenomad.adminbitebeyond.models.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthManager {
    fun createAccountWithEmail(email:String,password:String): Flow<AuthResponse>
    fun loginWithEmail(email:String,password: String):Flow<AuthResponse>

    //google signIn
    fun signInWithGoogle():Flow<AuthResponse>

    fun signInWithFacebook(activity: ComponentActivity, callbackManager: CallbackManager):Flow<AuthResponse>
}