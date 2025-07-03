package com.nativenomad.adminbitebeyond.domain.usecases.login

data class LoginUseCases (
    val loginWithEmail: LoginWithEmail,
    val signInWithGoogle: SignInWithGoogle,
    val signInWithFacebook: SignInWithFacebook,
    val createAccountWithEmail: CreateAccountWithEmail
)
