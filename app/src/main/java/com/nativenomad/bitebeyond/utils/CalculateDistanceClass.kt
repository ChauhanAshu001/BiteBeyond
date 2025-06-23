package com.nativenomad.bitebeyond.utils

import android.location.Location

class CalculateDistanceClass {

    fun calculateDistance(userLocation: Location, lat: Double, lng: Double):String{
        val targetLocation = Location("").apply {
            this.latitude = lat
            this.longitude = lng
        }
        val distanceInMeters = userLocation.distanceTo(targetLocation)

        return if (distanceInMeters < 1000) {
            "${distanceInMeters.toInt()} m"
        } else {
            String.format("%.1f km", distanceInMeters / 1000)
        }
    }
}
