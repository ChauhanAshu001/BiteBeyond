package com.nativenomad.bitebeyond.presentation.cart

sealed class CartEvents {
    object PlusClicked:CartEvents()
    object MinusClicked:CartEvents()
}