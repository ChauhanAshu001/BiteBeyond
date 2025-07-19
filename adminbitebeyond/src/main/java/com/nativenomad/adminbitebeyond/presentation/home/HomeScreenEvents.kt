package com.nativenomad.adminbitebeyond.presentation.home

sealed class HomeScreenEvents {
    object Nothing:HomeScreenEvents()
    object Loading:HomeScreenEvents()
    object Success:HomeScreenEvents()
    object Error:HomeScreenEvents()

}