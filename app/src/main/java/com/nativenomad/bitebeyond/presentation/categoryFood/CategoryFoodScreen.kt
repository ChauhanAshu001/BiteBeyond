package com.nativenomad.bitebeyond.presentation.categoryFood

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nativenomad.bitebeyond.presentation.categoryFood.components.CategoryFoodCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFoodScreen(
    categoryFoodViewModel: CategoryFoodViewModel= hiltViewModel(),
    navController: NavController,
    category: String
) {

    val items = categoryFoodViewModel.categoryFoodList.collectAsState()

    LaunchedEffect (category){
        categoryFoodViewModel.loadFoodItemsByCategory(category)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Category: $category") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(items.value) { item ->
                CategoryFoodCard(food = item,onEvent = categoryFoodViewModel::onEvent)
            }
        }
    }
}