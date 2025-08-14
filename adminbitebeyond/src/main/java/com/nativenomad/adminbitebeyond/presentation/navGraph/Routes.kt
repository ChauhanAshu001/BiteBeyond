package com.nativenomad.adminbitebeyond.presentation.navGraph

sealed class Routes(val route:String) {

    object OnBoardingScreen : Routes(route = "OnBoardingScreen")
    object SignUpScreen : Routes(route="SignUpScreen")
    object SignInScreen : Routes(route="SignInScreen")
    object RestaurantInfoScreen:Routes(route="restaurantInfoScreen")
    object LocationInfoScreen:Routes(route="locationInfoScreen")
    object MenuAddScreen:Routes(route="menuAddScreen")
    object MainScreen:Routes(route="mainScreen")
    object MyAccountScreen:Routes(route="myAccountScreen")
    object ModifyMenuScreen:Routes(route="modifyMenuScreen")
    object PastOrderScreen:Routes(route="pastOrderScreen")
    object HomeScreen:Routes(route="homeScreen")
    object OfferScreen:Routes(route="offerScreen")
    object MenuScreen:Routes(route="menuScreen")
    object ProfileScreen:Routes(route="profileScreen")
}