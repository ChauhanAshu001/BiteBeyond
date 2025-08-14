package com.nativenomad.bitebeyond.presentation.navgraph

sealed class Routes(val route:String) {
    object AppStartNavigation : Routes(route="appStartNavigation")
    object OnBoardingScreen : Routes(route="onBoardingScreen")
    object SignInScreen : Routes(route="signInScreen")
    object SignUpNavigation : Routes(route="signUpNavigation")
    object SignUpScreen : Routes(route="signUpScreen")
    object MainScreen:Routes(route="mainScreen")
    object MainScreenNavigation:Routes(route="mainScreenNavigation")

    object RestaurantDetailScreen:Routes(route="restaurantDetailScreen")
    object SearchScreen:Routes(route="Search Screen")
    object CategoryFoodScreen:Routes(route="CategoryFoodScreen")
    object MyAccountScreen:Routes(route = "myAccountScreen")
    object PastOrderScreen:Routes(route = "pastOrderScreen")
    object AiScreen:Routes(route="AiScreen")

}