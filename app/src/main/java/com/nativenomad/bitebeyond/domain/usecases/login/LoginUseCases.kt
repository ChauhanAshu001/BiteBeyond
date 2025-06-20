package com.nativenomad.bitebeyond.domain.usecases.login

data class LoginUseCases (
    val createAccountWithEmail: CreateAccountWithEmail,
    val loginWithEmail: LoginWithEmail,
    val signInWithGoogle: SignInWithGoogle,
    val signInWithFacebook: SignInWithFacebook
)
