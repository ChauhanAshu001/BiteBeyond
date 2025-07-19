package com.nativenomad.adminbitebeyond.presentation.bottomNav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.models.NavItems
import com.nativenomad.adminbitebeyond.presentation.OffersScreen.OffersScreen
import com.nativenomad.adminbitebeyond.presentation.home.HomeScreen
import com.nativenomad.adminbitebeyond.presentation.menu.MenuScreen
import com.nativenomad.adminbitebeyond.presentation.profile.ProfileScreen

@Composable
fun MainScreen(navController: NavController) {
    //rememberVectorPainter() function is used to convert an ImageVector into a Painter. Because in icon
    // i wanted to store images from Icons and sometimes i wanted image from drawable so I had to do this

    val navItems = listOf(
        NavItems("Home", rememberVectorPainter(Icons.Default.Home)),
        NavItems("Offers", painterResource(R.drawable.offers)), // AI bot logo
        NavItems("Menu", rememberVectorPainter(Icons.AutoMirrored.Filled.List)),
        NavItems("Profile", rememberVectorPainter(Icons.Default.Person))
    )
    val selectedState= remember{ mutableStateOf<String>("Home") }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItems.forEach {it->
                    NavigationBarItem(
                        selected = selectedState.value==it.label,
                        onClick = { selectedState.value = it.label },
                        icon = { Icon(it.icon, contentDescription = it.label) },
                        label = { Text(it.label) },
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
    ){innerPadding->
        ContentScreen(modifier= Modifier.padding(innerPadding),selectedState.value,navController)

    }


}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, screen: String, navController: NavController) {
    when(screen){

            "Home"-> HomeScreen()
            "Offers"-> OffersScreen()
            "Menu"-> MenuScreen()
            "Profile"-> ProfileScreen(navController=navController)
    }
}
