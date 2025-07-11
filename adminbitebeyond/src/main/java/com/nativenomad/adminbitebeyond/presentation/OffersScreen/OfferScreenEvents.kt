package com.nativenomad.adminbitebeyond.presentation.OffersScreen

sealed class OfferScreenEvents {
    object Nothing: OfferScreenEvents()
    object Success: OfferScreenEvents()
    object Error: OfferScreenEvents()
    object Loading: OfferScreenEvents()

}