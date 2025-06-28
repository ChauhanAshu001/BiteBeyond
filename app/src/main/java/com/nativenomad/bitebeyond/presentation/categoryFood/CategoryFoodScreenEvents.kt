package com.nativenomad.bitebeyond.presentation.categoryFood

sealed class CategoryFoodScreenEvents {
    object onPlusClicked:CategoryFoodScreenEvents()
    object onMinusClicked:CategoryFoodScreenEvents()
}