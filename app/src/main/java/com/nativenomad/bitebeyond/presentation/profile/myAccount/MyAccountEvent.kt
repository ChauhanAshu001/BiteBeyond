package com.nativenomad.bitebeyond.presentation.profile.myAccount

sealed class MyAccountEvent {
    object Nothing: MyAccountEvent()
    object Loading: MyAccountEvent()
    object Success: MyAccountEvent()
    object Failed: MyAccountEvent()
}