package com.nativenomad.adminbitebeyond.domain.usecases.databaseOp

data class DatabaseOpUseCases(
    val saveRestaurantData: SaveRestaurantData,
    val addMenuItem: AddMenuItem,
    val removeMenuItem: RemoveMenuItem,
    val getMyOffers: GetMyOffers,
    val getAllOffers: GetAllOffers,
    val addOffer: AddOffer,
    val deleteOffer: DeleteOffer,
    val saveCategoriesGlobally: SaveCategoryGlobally,
    val getFullMenu: GetFullMenu
)
