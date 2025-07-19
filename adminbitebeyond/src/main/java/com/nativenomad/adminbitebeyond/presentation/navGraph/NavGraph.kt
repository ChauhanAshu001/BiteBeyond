package com.nativenomad.adminbitebeyond.presentation.navGraph

import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nativenomad.adminbitebeyond.presentation.bottomNav.MainScreen
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.locationInfo.LocationInfoNavigationEvent
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.locationInfo.LocationInfoScreen
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.locationInfo.LocationInfoViewModel
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd.MenuAddNavigationEvent
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd.MenuAddScreen
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd.MenuAddViewModel
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo.RestaurantInfoNavigationEvent
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo.RestaurantInfoScreen
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo.RestaurantInfoViewModel
import com.nativenomad.adminbitebeyond.presentation.onboarding.OnBoardingEvent
import com.nativenomad.adminbitebeyond.presentation.onboarding.OnBoardingScreen
import com.nativenomad.adminbitebeyond.presentation.onboarding.OnBoardingViewModel
import com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu.ModifyMenuNavigationEvent
import com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu.ModifyMenuScreen
import com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu.ModifyMenuViewModel
import com.nativenomad.adminbitebeyond.presentation.profile.myAccount.MyAccountScreen
import com.nativenomad.adminbitebeyond.presentation.profile.pastOrders.PastOrderScreen
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signin.SignInNavigationEvent
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signin.SignInScreen
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signin.SignInViewModel
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signup.SignUpNavigationEvent
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signup.SignUpScreen
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signup.SignUpViewmodel
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
            val viewModel:SignUpViewmodel= hiltViewModel()
            LaunchedEffect(true) {
                viewModel.navigateEvent.collectLatest { signUpNavigationEvent ->
                    when (signUpNavigationEvent) {
                        is SignUpNavigationEvent.NavigateToSignIn -> {
                            navController.navigate(Routes.SignInScreen.route)
                        }

                        is SignUpNavigationEvent.NavigateToRestaurantInfoScreen -> {
                            navController.navigate(Routes.RestaurantInfoScreen.route) {
                                popUpTo(Routes.SignUpScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                }
            }
            SignUpScreen (onEvent = {signUpNavigationEvent->
                viewModel.onEvent(signUpNavigationEvent)
            })
        }
        composable(route=Routes.SignInScreen.route) {
            val viewmodel: SignInViewModel = hiltViewModel()
            LaunchedEffect(true){
                viewmodel.navigateEvent.collectLatest { signInNavigationEvent ->
                    when(signInNavigationEvent){
                        is SignInNavigationEvent.NavigateToSignUp->{
                            navController.navigate(Routes.SignUpScreen.route)
                        }
                        is SignInNavigationEvent.NavigateToHome->{
                            navController.navigate(Routes.MainScreen.route){
                                    popUpTo(Routes.SignInScreen.route){
                                        inclusive=true
                                    }
                                }
//
                        }
                    }
                }
            }

            SignInScreen(onEvent = {signInNavigationEvent->
                viewmodel.onEvent(signInNavigationEvent)
            })

        }
        composable(Routes.RestaurantInfoScreen.route) {
            val viewmodel:RestaurantInfoViewModel= hiltViewModel()
            LaunchedEffect(Unit) {
                viewmodel.navigateEvent.collectLatest { restaurantInfoNavigationEvent ->
                    when (restaurantInfoNavigationEvent) {
                        is RestaurantInfoNavigationEvent.NavigateToLocationInfoScreen -> {
                            navController.navigate(Routes.LocationInfoScreen.route)
                        }
                    }
                }
            }
            RestaurantInfoScreen(onEvent = {restaurantInfoNavigationEvent ->
                viewmodel.onEvent(restaurantInfoNavigationEvent)
            })
        }

        composable(Routes.LocationInfoScreen.route) {
            val viewmodel:LocationInfoViewModel= hiltViewModel()
            LaunchedEffect(Unit) {
                viewmodel.navigateEvent.collectLatest { locationInfoNavigationEvent ->
                    when (locationInfoNavigationEvent) {
                        is LocationInfoNavigationEvent.NavigateToMenuAdd -> {
                            navController.navigate(Routes.MenuAddScreen.route)
                        }
                    }
                }
            }
            LocationInfoScreen(
                onEvent = {locationInfoNavigationEvent ->
                    viewmodel.onEvent(locationInfoNavigationEvent)
                }
            )
        }

        composable(Routes.MenuAddScreen.route) {
            val viewmodel:MenuAddViewModel= hiltViewModel()
            LaunchedEffect(Unit) {
                viewmodel.navigateEvent.collectLatest { menuAddNavigationEvent ->
                    when(menuAddNavigationEvent){
                        is MenuAddNavigationEvent.NavigateToHome->{
                            navController.navigate(Routes.MainScreen.route)
                        }
                    }
                }
            }
            MenuAddScreen(onEvent = {menuAddNavigationEvent ->
                viewmodel.onEvent(menuAddNavigationEvent)
            })
        }

        composable(route=Routes.MainScreen.route) {
            MainScreen(navController=navController)
        }

        composable(route=Routes.MyAccountScreen.route) {
            MyAccountScreen()
        }

        composable(route=Routes.ModifyMenuScreen.route) {
            val viewmodel:ModifyMenuViewModel= hiltViewModel()
            val context= LocalContext.current
            LaunchedEffect(Unit) {
                viewmodel.navigateEvent.collectLatest { modifyMenuNavigationEvent ->
                    when(modifyMenuNavigationEvent){
                        is ModifyMenuNavigationEvent.NavigateToProfileScreen->{

                            Toast.makeText(context,"Menu Saved",Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.MainScreen.route)
                        }
                    }
                }
            }
            ModifyMenuScreen(onEvent = {modifyMenuNavigationEvent->
                viewmodel.onEvent(modifyMenuNavigationEvent)})
        }
        composable(route=Routes.PastOrderScreen.route) {
            PastOrderScreen()
        }
    }

}