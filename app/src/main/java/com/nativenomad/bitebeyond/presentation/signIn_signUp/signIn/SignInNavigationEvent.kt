package com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn

sealed class SignInNavigationEvent {
    object NavigateToSignUp: SignInNavigationEvent()
    object NavigateToHome: SignInNavigationEvent() //used when already existing Account is logged in successfully
    object NavigateToHomeFromSkip:SignInNavigationEvent()  //used when skip is pressed
}