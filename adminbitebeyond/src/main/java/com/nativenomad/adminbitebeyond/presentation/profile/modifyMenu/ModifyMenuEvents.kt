package com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu

sealed class ModifyMenuEvents {
    object Nothing:ModifyMenuEvents()
    object Error:ModifyMenuEvents()
    object Success:ModifyMenuEvents()
    object Loading:ModifyMenuEvents()
}