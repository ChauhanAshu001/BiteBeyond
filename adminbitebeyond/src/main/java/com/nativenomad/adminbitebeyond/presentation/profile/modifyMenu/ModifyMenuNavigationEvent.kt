package com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu

import com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd.MenuAddNavigationEvent

sealed class ModifyMenuNavigationEvent {
    object NavigateToProfileScreen: ModifyMenuNavigationEvent()
}