package com.nativenomad.adminbitebeyond.presentation.bottomNav



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.models.NavItems
import com.nativenomad.adminbitebeyond.presentation.OffersScreen.OffersScreen
import com.nativenomad.adminbitebeyond.presentation.home.HomeScreen
import com.nativenomad.adminbitebeyond.presentation.menu.MenuScreen
import com.nativenomad.adminbitebeyond.presentation.navGraph.Routes
import com.nativenomad.adminbitebeyond.presentation.profile.ProfileScreen

@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()

    val navItems = listOf(
        NavItems("Home", rememberVectorPainter(Icons.Default.Home), Routes.HomeScreen.route),
        NavItems("Offers", painterResource(R.drawable.offers), Routes.OfferScreen.route),
        NavItems("Menu", rememberVectorPainter(Icons.AutoMirrored.Filled.List), Routes.MenuScreen.route),
        NavItems("Profile", rememberVectorPainter(Icons.Default.Person), Routes.ProfileScreen.route)
    )

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
                            selectedIconColor = colorResource(id= R.color.lightGreen),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = colorResource(id=R.color.lightGreen),
                            unselectedTextColor = Color.Gray,
                            indicatorColor = colorResource(id=R.color.lightGreen).copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "homeScreen",

        ) {
            composable(route=Routes.HomeScreen.route) { HomeScreen() }
            composable(route=Routes.OfferScreen.route) { OffersScreen() }
            composable(route=Routes.MenuScreen.route) { MenuScreen() }
            composable(route=Routes.ProfileScreen.route) { ProfileScreen(navController = navController) } //here global nav controller is passed because bottomNavController don't have composable for MyAccountScreen,PastOrderScreen,ModifyRestaurantMenu Screen.
        }
    }
}
