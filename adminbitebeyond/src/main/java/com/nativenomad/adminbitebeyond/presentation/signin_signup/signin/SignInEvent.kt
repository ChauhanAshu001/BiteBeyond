package com.nativenomad.adminbitebeyond.presentation.signin_signup.signin

sealed class SignInEvent {
    object Nothing: SignInEvent()        /*this is used to store some initial value in _uiState Variable we'll create in viewmodel because initially
    we can't store Success or Error or Loading in _uiState Variable
    */
    object Success: SignInEvent()
    object Error: SignInEvent()
    object Loading: SignInEvent()
}