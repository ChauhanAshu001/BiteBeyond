package com.nativenomad.bitebeyond.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.nativenomad.bitebeyond.presentation.login.SignUpScreen
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingNavigationEvent
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingScreen
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NavGraph(
    startDestination:String
){
    val navController= rememberNavController()

    NavHost(navController=navController,startDestination=startDestination){
        navigation(
            route = Routes.AppStartNavigation.route, //just the identifier of this navigation graph/route
            startDestination=Routes.OnBoardingScreen.route
        ){
            composable(route=Routes.OnBoardingScreen.route){
                val viewModel: OnBoardingViewModel = hiltViewModel() //creating object of viewmodel whose creation is handled by hilt
                LaunchedEffect(true) { // Or key1 = Unit
                    viewModel.navigationEvent.collectLatest { event ->
                        when (event) {
                            is OnBoardingNavigationEvent.NavigateToSignUpScreen -> {
                                navController.navigate(Routes.SignUpNavigation.route) {
                                    // Optional: Pop OnBoarding from back stack so user can't go back to it
//                                    popUpTo(Routes.OnBoardingScreen.route) {
//                                        inclusive = true
//                                    } //for this I am going to do this in Main View Model

                                }
                            }
                        }
                    }
                }
                OnBoardingScreen(event={onBoardingEvent ->
                    viewModel.onEvent(onBoardingEvent)
                })
            }
        }
        navigation(route=Routes.SignUpNavigation.route, startDestination=Routes.SignUpScreen.route){
            composable(route=Routes.SignUpScreen.route){
                SignUpScreen()
            }
        }
    }
}