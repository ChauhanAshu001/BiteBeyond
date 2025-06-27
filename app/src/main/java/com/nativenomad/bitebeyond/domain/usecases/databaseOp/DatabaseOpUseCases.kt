package com.nativenomad.bitebeyond.domain.usecases.databaseOp

data class DatabaseOpUseCases(
    val getCategories: GetCategories,
    val getRestaurants: GetRestaurants,
    val getMenu: GetMenu,
    val getOffers: GetOffers,
    val getPromoCodeRestaurantMap: GetPromoCodeRestaurantMap
)