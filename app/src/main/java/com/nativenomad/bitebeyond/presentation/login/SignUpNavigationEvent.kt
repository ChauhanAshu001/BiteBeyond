package com.nativenomad.bitebeyond.presentation.login

sealed class SignUpNavigationEvent {
    object NavigateToLogin:SignUpNavigationEvent()
    object NavigateToHome:SignUpNavigationEvent()
}