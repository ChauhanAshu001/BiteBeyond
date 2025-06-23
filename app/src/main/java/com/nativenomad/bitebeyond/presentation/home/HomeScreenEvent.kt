package com.nativenomad.bitebeyond.presentation.home

sealed class HomeScreenEvent {
    object Nothing:HomeScreenEvent()
    object Loading:HomeScreenEvent()
    object Success:HomeScreenEvent()
}