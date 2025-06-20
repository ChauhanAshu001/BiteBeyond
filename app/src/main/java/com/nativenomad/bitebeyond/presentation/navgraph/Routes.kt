package com.nativenomad.bitebeyond.presentation.navgraph

sealed class Routes(val route:String) {
    object AppStartNavigation : Routes(route="appStartNavigation")
    object OnBoardingScreen : Routes(route="onBoardingScreen")
    object SignInScreen : Routes(route="signInScreen")
    object SignUpNavigation : Routes(route="signUpNavigation")
    object SignUpScreen : Routes(route="signUpScreen")
    object HomeScreen : Routes(route="homeScreen")
    object HomeScreenNavigation:Routes(route="HomeScreenNavigation")
}