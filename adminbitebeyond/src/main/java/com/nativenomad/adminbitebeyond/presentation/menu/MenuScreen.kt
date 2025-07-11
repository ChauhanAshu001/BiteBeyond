package com.nativenomad.adminbitebeyond.presentation.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.common.MenuItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(menuViewModel: MenuViewModel = hiltViewModel()) {
    val menu = menuViewModel.menu.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Restaurant Menu",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.RestaurantMenu,
                        contentDescription = "Menu Icon",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.emeraldGreen)
                )
            )
        }
    ) { paddingValues ->

        if (menu.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No items in the menu yet.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id=R.color.indicatorColor)
                )

            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(menu.value) { item ->
                    MenuItemCard(
                        foodItem = item,
                        onDeleteClick = { menuViewModel.deleteItem(it) }
                    )
                }
            }
        }

    }
}
