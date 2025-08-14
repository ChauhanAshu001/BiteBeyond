package com.nativenomad.bitebeyond.presentation.aiModel

sealed class AiScreenEvents {
    object Nothing:AiScreenEvents()
    object Success:AiScreenEvents()
    object Loading:AiScreenEvents()
    object Error:AiScreenEvents()

}