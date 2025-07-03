package com.nativenomad.adminbitebeyond.presentation.signin_signup.signup

sealed class SignUpNavigationEvent {
    object NavigateToSignIn: SignUpNavigationEvent()
    object NavigateToHome: SignUpNavigationEvent()

}