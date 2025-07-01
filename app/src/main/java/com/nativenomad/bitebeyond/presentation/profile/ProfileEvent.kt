package com.nativenomad.bitebeyond.presentation.profile

sealed class ProfileEvent {
    object Nothing:ProfileEvent()
    object Loading:ProfileEvent()
    object Success:ProfileEvent()
    object Failed:ProfileEvent()
}