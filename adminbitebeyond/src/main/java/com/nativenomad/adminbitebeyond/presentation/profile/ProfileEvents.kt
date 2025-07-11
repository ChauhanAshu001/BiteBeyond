package com.nativenomad.adminbitebeyond.presentation.profile

sealed class ProfileEvents {
    object Nothing:ProfileEvents()
    object Success:ProfileEvents()
    object Loading:ProfileEvents()
    object Error:ProfileEvents()
}