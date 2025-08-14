package com.nativenomad.bitebeyond.presentation.bottomNav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.models.NavItems
import com.nativenomad.bitebeyond.presentation.aiModel.BotIntroScreen
import com.nativenomad.bitebeyond.presentation.cart.CartScreen
import com.nativenomad.bitebeyond.presentation.home.HomeScreen
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
import com.nativenomad.bitebeyond.presentation.profile.ProfileScreen

@Composable
fun MainScreen(navController: NavController) {
    //rememberVectorPainter() function is used to convert an ImageVector into a Painter. Because in icon
    // i wanted to store images from Icons and sometimes i wanted image from drawable so I had to do this

    val navItems = listOf(
        NavItems("Home", rememberVectorPainter(Icons.Default.Home),Routes.HomeScreen.route),
        NavItems("AI", painterResource(R.drawable.bot),Routes.AIEnterScreen.route), // AI bot logo
        NavItems("Cart", rememberVectorPainter(Icons.Default.ShoppingCart),Routes.CartScreen.route),
        NavItems("Profile", rememberVectorPainter(Icons.Default.Person),Routes.ProfileScreen.route)
    )

    val bottomNavController = rememberNavController()

//    Scaffold (
//        modifier = Modifier.fillMaxSize(),
//        bottomBar = {
//            NavigationBar {
//                navItems.forEach {it->
//                    NavigationBarItem(
//                        selected = selectedState.value==it.label,
//                        onClick = { selectedState.value = it.label },
//                        icon = { Icon(it.icon, contentDescription = it.label) },
//                        label = { Text(it.label) },
//                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = colorResource(id=R.color.lightOrange),
//                            unselectedIconColor = Color.Gray,
//                            selectedTextColor = colorResource(id=R.color.lightOrange),
//                            unselectedTextColor = Color.Gray,
//                            indicatorColor = colorResource(id=R.color.lightOrange).copy(alpha = 0.15f)
//                        )
//                    )
//                }
//            }
//        }
//    ){innerPadding->
//        ContentScreen(modifier=Modifier.padding(innerPadding),selectedState.value,navController)
//
//    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id= R.color.lightOrange),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = colorResource(id=R.color.lightOrange),
                            unselectedTextColor = Color.Gray,
                            indicatorColor = colorResource(id=R.color.lightOrange).copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HomeScreen.route,

            ) {
            composable(route= Routes.HomeScreen.route) { HomeScreen(navController=navController) }
            composable(route=Routes.AIEnterScreen.route) {BotIntroScreen(navController=navController)}
            composable(route=Routes.CartScreen.route) { CartScreen(navController = navController) }
            composable(route=Routes.ProfileScreen.route) { ProfileScreen(navController = navController) } //here global nav controller is passed because bottomNavController don't have composable for MyAccountScreen,PastOrderScreen,ModifyRestaurantMenu Screen.
        }
    }


}


