package com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp

sealed class SignUpEvent {
    object Nothing: SignUpEvent()        /*this is used to store some initial value in _uiState Variable we'll create in viewmodel because initially
    we can't store Success or Error or Loading in _uiState Variable
    */
    object Success: SignUpEvent()
    object Error: SignUpEvent()
    object Loading: SignUpEvent()

}