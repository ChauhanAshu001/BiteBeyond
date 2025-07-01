package com.nativenomad.bitebeyond.domain.repository

import android.net.Uri
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow

interface ProfileDataRepository {
    val name: MutableStateFlow<String>
    val email: MutableStateFlow<String>
    val gender: MutableStateFlow<String>
    val phoneNumber: MutableStateFlow<String>
    val imageUrl: MutableStateFlow<Uri?>

    suspend fun getUserData()
    suspend fun saveUserData(userId: String, user: UserProfile)



}