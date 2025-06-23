package com.nativenomad.bitebeyond.presentation.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.models.NavItems

@Composable
fun BottomNavigation() {
    //rememberVectorPainter() function is used to convert an ImageVector into a Painter. Because in icon
    // i wanted to store images from Icons and sometimes i wanted image from drawable so I had to do this
    val navItems = listOf(
        NavItems("Home", rememberVectorPainter(Icons.Default.Home)),
        NavItems("AI", painterResource(R.drawable.bot)), // AI bot logo
        NavItems("Cart", rememberVectorPainter(Icons.Default.ShoppingCart)),
        NavItems("Profile", rememberVectorPainter(Icons.Default.Person))
    )
    NavigationBar {
        navItems.forEach {
            NavigationBarItem(
                selected = false,
                onClick = { /* TODO: Handle navigation */ },
                icon = { Icon(it.icon, contentDescription = it.label) },
                label = { Text(it.label) }
            )
        }
    }

}