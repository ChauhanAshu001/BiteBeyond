package com.nativenomad.adminbitebeyond.presentation.profile.myAccount

sealed class MyAccountEvents {
    object Nothing: MyAccountEvents()
    object Success: MyAccountEvents()
    object Loading: MyAccountEvents()
    object Error: MyAccountEvents()
}