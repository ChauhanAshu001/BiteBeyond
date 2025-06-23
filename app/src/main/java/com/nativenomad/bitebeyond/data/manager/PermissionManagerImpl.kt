package com.nativenomad.bitebeyond.data.manager

import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import android.Manifest
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.nativenomad.bitebeyond.domain.manager.PermissionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PermissionManagerImpl@Inject constructor(
    @ApplicationContext private val application: Application
) :PermissionManager {
    override suspend fun getUserLocation(): Location? {
        if (ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
            try {
                 return fusedLocationClient.lastLocation.await() // From kotlinx-coroutines-play-services
            } catch (e: Exception) {
                Log.e("Location", "Failed to get last location", e)
                Toast.makeText(application, "Unable to fetch location", Toast.LENGTH_SHORT).show()
                return null
            }

        }
        else{
            Toast.makeText(application, "Allow Location Permission to fetch Location", Toast.LENGTH_SHORT).show()
            return null
        }
    }
}