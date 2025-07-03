package com.nativenomad.adminbitebeyond.presentation.navGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.nativenomad.adminbitebeyond.presentation.onboarding.OnBoardingEvent
import com.nativenomad.adminbitebeyond.presentation.onboarding.OnBoardingScreen
import com.nativenomad.adminbitebeyond.presentation.onboarding.OnBoardingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NavGraph(
    startDestination:String
) {
    val navController= rememberNavController()
    NavHost(
        navController=navController,
        startDestination=startDestination,
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
        navigation(route=Routes.AppStartNavigation.route, startDestination = Routes.OnBoardingScreen.route){
            composable(route=Routes.OnBoardingScreen.route){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                LaunchedEffect(true) { // Or key1 = Unit
                    viewModel.navigationEvent.collectLatest { event ->
                        when (event) {
                            is OnBoardingEvent.NavigateToSignUpScreen -> {
                                navController.navigate(Routes.SignUpScreen.route)
                            }
                            else->{}
                        }
                    }
                }
                OnBoardingScreen(event = {onBoardingEvent->
                    viewModel.onEvent(onBoardingEvent)
                })
            }

            composable(route=Routes.SignUpScreen.route){
                TODO()
            }
        }
    }
}