package com.nativenomad.adminbitebeyond.presentation.menu

sealed class MenuScreenEvent {
    object Nothing:MenuScreenEvent()
    object Loading:MenuScreenEvent()
    object Success:MenuScreenEvent()
    object Error:MenuScreenEvent()

}