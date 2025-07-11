package com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd



sealed class MenuAddEvent {
    object Nothing: MenuAddEvent()
    object Success: MenuAddEvent()
    object Error: MenuAddEvent()
    object Loading: MenuAddEvent()

}