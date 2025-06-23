package com.nativenomad.bitebeyond.domain.manager

import android.location.Location

interface PermissionManager {
    suspend fun getUserLocation(): Location?
}