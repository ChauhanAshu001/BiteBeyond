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
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.models.NavItems
import com.nativenomad.bitebeyond.presentation.aiModel.BotIntroScreen
import com.nativenomad.bitebeyond.presentation.cart.CartScreen
import com.nativenomad.bitebeyond.presentation.home.HomeScreen
import com.nativenomad.bitebeyond.presentation.profile.ProfileScreen

@Composable
fun MainScreen(navController: NavController) {
    //rememberVectorPainter() function is used to convert an ImageVector into a Painter. Because in icon
    // i wanted to store images from Icons and sometimes i wanted image from drawable so I had to do this

    val navItems = listOf(
        NavItems("Home", rememberVectorPainter(Icons.Default.Home)),
        NavItems("AI", painterResource(R.drawable.bot)), // AI bot logo
        NavItems("Cart", rememberVectorPainter(Icons.Default.ShoppingCart)),
        NavItems("Profile", rememberVectorPainter(Icons.Default.Person))
    )
    val selectedState=remember{ mutableStateOf<String>("Home") }

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
                            selectedIconColor = colorResource(id=R.color.lightOrange),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = colorResource(id=R.color.lightOrange),
                            unselectedTextColor = Color.Gray,
                            indicatorColor = colorResource(id=R.color.lightOrange).copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ){innerPadding->
        ContentScreen(modifier=Modifier.padding(innerPadding),selectedState.value,navController)

    }


}

@Composable
fun ContentScreen(modifier: Modifier=Modifier, screen: String,navController: NavController) {
    when(screen){
        "Home"->HomeScreen(navController=navController)
        "AI"-> BotIntroScreen(navController=navController)
        "Cart"->CartScreen(navController = navController)
        "Profile"-> ProfileScreen(navController = navController)

    }
}
