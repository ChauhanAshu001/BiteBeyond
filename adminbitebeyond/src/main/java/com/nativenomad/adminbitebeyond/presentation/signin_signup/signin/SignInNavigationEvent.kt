package com.nativenomad.adminbitebeyond.presentation.signin_signup.signin

sealed class SignInNavigationEvent {
    object NavigateToSignUp: SignInNavigationEvent()
    object NavigateToHome: SignInNavigationEvent()
}