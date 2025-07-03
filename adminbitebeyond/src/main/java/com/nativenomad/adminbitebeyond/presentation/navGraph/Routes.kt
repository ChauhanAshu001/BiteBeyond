package com.nativenomad.adminbitebeyond.presentation.navGraph

sealed class Routes(val route:String) {
    object AppStartNavigation : Routes(route = "App_Start_Navigation")
    object OnBoardingScreen : Routes(route = "OnBoardingScreen")
    object SignUpScreen:Routes(route="SignUpScreen")
}