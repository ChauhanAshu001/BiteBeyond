package com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp

sealed class SignUpNavigationEvent {
    object NavigateToSignIn: SignUpNavigationEvent()
    object NavigateToHome: SignUpNavigationEvent()
    object NavigateToHomeFromSkip:SignUpNavigationEvent()
}