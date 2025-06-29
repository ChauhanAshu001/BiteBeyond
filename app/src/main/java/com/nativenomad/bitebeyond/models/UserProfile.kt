package com.nativenomad.bitebeyond.models

import android.net.Uri

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val imageUrl: String? = null,
    val gender: String = "",
    val phoneNumber: String = ""
)