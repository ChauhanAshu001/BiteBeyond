package com.nativenomad.bitebeyond.domain.usecases.permissions

import android.location.Location
import com.nativenomad.bitebeyond.domain.manager.PermissionManager

class GetUserLocation(private val permissionManager: PermissionManager) {
    suspend operator fun invoke():Location?{
        return permissionManager.getUserLocation()
    }
}