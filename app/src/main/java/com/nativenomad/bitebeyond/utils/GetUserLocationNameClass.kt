package com.nativenomad.bitebeyond.utils

import android.app.Application
import android.location.Geocoder
import android.location.Location
import java.util.Locale

class GetUserLocationNameClass {
    fun getUserLocationName(context: Application, location: Location): String {
        try{
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                return "${address.locality}, ${address.countryName}"  // e.g., "Delhi, India"
            } else {
                return "Unknown Location"
            }
        }
        catch(e:Exception){
            return "Unknown Location"
        }
    }
}