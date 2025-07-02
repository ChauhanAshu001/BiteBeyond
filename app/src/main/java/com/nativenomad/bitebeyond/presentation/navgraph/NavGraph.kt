package com.nativenomad.bitebeyond.presentation.navgraph

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.nativenomad.bitebeyond.presentation.bottomNav.MainScreen
import com.nativenomad.bitebeyond.presentation.categoryFood.CategoryFoodScreen
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingEvent
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.SignInScreen
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp.SignUpNavigationEvent
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp.SignUpScreen
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp.SignUpViewmodel
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingScreen
import com.nativenomad.bitebeyond.presentation.onboarding.OnBoardingViewModel
import com.nativenomad.bitebeyond.presentation.restaurantDetails.RestaurantDetailsScreen
import com.nativenomad.bitebeyond.presentation.search.SearchScreen
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.SignInNavigationEvent
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.SignInViewModel
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
                            is OnBoardingEvent.NavigateToSignUpScreen -> {
                                navController.navigate(Routes.SignUpNavigation.route)
                            }
                            else->{}
                        }
                    }
                }
                OnBoardingScreen(event={onBoardingEvent ->
                    viewModel.onEvent(onBoardingEvent)
                })
            }
        }
        navigation(route=Routes.SignUpNavigation.route, startDestination=Routes.SignUpScreen.route) {
            composable(route = Routes.SignUpScreen.route) {
                val viewModel: SignUpViewmodel = hiltViewModel()
                LaunchedEffect(true) {
                    viewModel.navigateEvent.collectLatest { signUpNavigationEvent ->
                        when (signUpNavigationEvent) {
                            is SignUpNavigationEvent.NavigateToSignIn -> {
                                navController.navigate(Routes.SignInScreen.route)
                            }

                            is SignUpNavigationEvent.NavigateToHome -> {
                                navController.navigate(Routes.MainScreenNavigation.route) {
                                    popUpTo(Routes.SignUpScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                            is SignUpNavigationEvent.NavigateToHomeFromSkip -> {
                                navController.navigate(Routes.MainScreenNavigation.route)
                            }
                        }

                    }
                }
                SignUpScreen(onEvent = { signUpNavigationEvent ->
                    viewModel.onEvent(signUpNavigationEvent)
                })
            }
            composable(route = Routes.SignInScreen.route) {
                val viewmodel:SignInViewModel= hiltViewModel()
                LaunchedEffect(true){
                    viewmodel.navigateEvent.collectLatest { signInNavigationEvent ->
                        when(signInNavigationEvent){
                            is SignInNavigationEvent.NavigateToSignUp->{
                                navController.navigate(Routes.SignUpScreen.route)
                            }
                            is SignInNavigationEvent.NavigateToHome->{
                                navController.navigate(Routes.MainScreenNavigation.route){
                                    popUpTo(Routes.SignInScreen.route){
                                        inclusive=true
                                    }
                                }
                            }
                            is SignInNavigationEvent.NavigateToHomeFromSkip->{
                                navController.navigate(Routes.MainScreenNavigation.route)  //popping is not done in this case so that user can login anytime by going back
                            }
                        }
                    }
                }

                SignInScreen(onEvent = {signInNavigationEvent->
                    viewmodel.onEvent(signInNavigationEvent)
                })

            }

        }

        //sub-graph starting from main screen
        navigation(route=Routes.MainScreenNavigation.route,startDestination=Routes.MainScreen.route){
            composable(route=Routes.MainScreen.route) {
                MainScreen(navController)
            }
            composable(
                route = "${Routes.RestaurantDetailScreen.route}/{restaurantName}/{distanceKm}/{imageUrl}/{rating}",
                arguments = listOf(
                    navArgument("restaurantName"){type=NavType.StringType},
                    navArgument("distanceKm") { type = NavType.StringType },
                    navArgument("imageUrl") { type = NavType.StringType },
                    navArgument("rating"){type=NavType.FloatType}
                )
            ) {
                val restaurantName = it.arguments?.getString("restaurantName") ?: ""
                val distance = it.arguments?.getString("distanceKm") ?: "0.0"
                val imageUrl = Uri.decode(it.arguments?.getString("imageUrl") ?: "")
                val rating=it.arguments?.getFloat("rating")?:0f

                RestaurantDetailsScreen(
                    restaurantName = restaurantName, distanceKm = distance, imageUrl = imageUrl,
                    rating =rating)
            }

            composable(route=Routes.SearchScreen.route) {
                SearchScreen(navController=navController)
            }
            composable(
                route="${Routes.CategoryFoodScreen.route}/{categoryName}",
                arguments=listOf(
                    navArgument("categoryName"){type=NavType.StringType}
                )
            ) {
                val categoryName = Uri.decode(it.arguments?.getString("categoryName") ?: "")
                CategoryFoodScreen(category=categoryName, navController = navController)
            }
        }
    }
}