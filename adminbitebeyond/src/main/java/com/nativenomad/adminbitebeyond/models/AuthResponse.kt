package com.nativenomad.adminbitebeyond.models

sealed class AuthResponse{
    object Success:AuthResponse()
    data class Error(val message:String):AuthResponse()
}
