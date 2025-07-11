package com.nativenomad.adminbitebeyond.presentation.enteringScreens.locationInfo

sealed class LocationInfoEvents {
    object Nothing: LocationInfoEvents()
    object Success: LocationInfoEvents()
    object Error: LocationInfoEvents()
    object Loading: LocationInfoEvents()
}