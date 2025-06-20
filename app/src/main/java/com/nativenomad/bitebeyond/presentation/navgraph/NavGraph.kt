package com.nativenomad.bitebeyond.presentation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.nativenomad.bitebeyond.presentation.home.HomeScreen
import com.nativenomad.bitebeyond.presentation.login.SignInScreen
import com.nativenomad.bitebeyond.presentation.login.SignUpNavigationEvent
import com.nativenomad.bitebeyond.presentation.login.SignUpScreen
import com.nativenomad.bitebeyond.presentation.login.SignUpViewmodel
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingNavigationEvent
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingScreen
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NavGraph(
    startDestination:String
){
    val navController= rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )+ fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )+ fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )+ fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )+ fadeOut(animationSpec = tween(300))
        }
    ){
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
                val viewModel: SignUpViewmodel = hiltViewModel()
                LaunchedEffect (true){
                    viewModel.navigateEvent.collectLatest { signUpNavigationEvent ->
                        when(signUpNavigationEvent){
                            is SignUpNavigationEvent.NavigateToLogin->{
                                navController.navigate(Routes.SignInScreen.route)
                            }
                            is SignUpNavigationEvent.NavigateToHome->{
                                navController.navigate(Routes.HomeScreen.route){
                                    popUpTo(Routes.SignUpScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                    }
                }
                SignUpScreen(event={signUpNavigationEvent ->
                    viewModel.onEvent(signUpNavigationEvent)
                })
            }
            composable(route=Routes.SignInScreen.route) {
                SignInScreen()

            }
            composable(route=Routes.HomeScreen.route){
                HomeScreen()
            }
        }
        navigation(route=Routes.HomeScreenNavigation.route, startDestination = Routes.HomeScreen.route){
            composable(route=Routes.HomeScreen.route){
                HomeScreen()
            }
        }
    }
}