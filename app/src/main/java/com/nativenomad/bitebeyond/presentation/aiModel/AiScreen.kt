package com.nativenomad.bitebeyond.presentation.aiModel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.bitebeyond.presentation.aiModel.components.AITextField
import com.nativenomad.bitebeyond.presentation.aiModel.components.TextDropDown
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.SignInEvent

@Composable
fun AiScreen(aiViewModel: AiViewModel= hiltViewModel()) {

    val prepTime = aiViewModel.prepTime.collectAsState()
    val cookTime = aiViewModel.cookTime.collectAsState()
    val totalTime = aiViewModel.totalTime.collectAsState()
    val cuisine = aiViewModel.cuisine.collectAsState()
    val course = aiViewModel.course.collectAsState()
    val diet = aiViewModel.diet.collectAsState()
    val recipeName = aiViewModel.recipeName.collectAsState()
    val uiState = aiViewModel.uiState.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val cuisineOptions = listOf("Indian", "South Indian Recipes", "Andhra", "Udupi", "Mexican",
        "Fusion", "Continental", "Bengali Recipes", "Punjabi", "Chettinad",
        "Tamil Nadu", "Maharashtrian Recipes", "North Indian Recipes",
        "Italian Recipes", "Sindhi", "Thai", "Chinese", "Kerala Recipes",
        "Gujarati Recipes", "Coorg", "Rajasthani", "Asian",
        "Middle Eastern", "Coastal Karnataka", "European", "Kashmiri",
        "Karnataka", "Lucknowi", "Hyderabadi", "Side Dish", "Goan Recipes",
        "Arab", "Assamese", "Bihari", "Malabar", "Himachal", "Awadhi",
        "Cantonese", "North East India Recipes", "Sichuan", "Mughlai",
        "Japanese", "Mangalorean", "Vietnamese", "British",
        "North Karnataka", "Parsi Recipes", "Greek", "Nepalese",
        "Oriya Recipes", "French", "Indo Chinese", "Konkan",
        "Mediterranean", "Sri Lankan", "Haryana", "Uttar Pradesh",
        "Malvani", "Indonesian", "African", "Shandong", "Korean",
        "American", "Kongunadu", "Pakistani", "Caribbean",
        "South Karnataka", "Appetizer", "Uttarakhand-North Kumaon",
        "World Breakfast", "Malaysian", "Dessert", "Hunan", "Dinner",
        "Snack", "Jewish", "Burmese", "Afghan", "Brunch", "Jharkhand",
        "Nagaland", "Lunch"
    )
    val courseOptions = listOf("Side Dish", "Main Course", "South Indian Breakfast", "Lunch",
        "Snack", "High Protein Vegetarian", "Dinner", "Appetizer",
        "Indian Breakfast", "Dessert", "North Indian Breakfast",
        "One Pot Dish", "Breakfast", "Non Vegeterian", "Vegetarian",
        "Eggetarian", "Brunch", "Vegan",
        "Sugar Free Diet"
    )
    val dietOptions = listOf("Diabetic Friendly", "Vegetarian", "High Protein Vegetarian",
        "Non Vegeterian", "High Protein Non Vegetarian", "Eggetarian",
        "Vegan", "No Onion No Garlic (Sattvic)", "Gluten Free",
        "Sugar Free Diet"
    )

    when(uiState.value){
        is AiScreenEvents.Error->{
            errorMessage.value="Please fill all the fields."
        }
        is AiScreenEvents.Loading->{
            errorMessage.value=null
        }
        else->{
            errorMessage.value=null
        }
    }

    val scrollState = rememberScrollState()
    Scaffold{ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8E1))
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            Column {
                Text(
                    "Let me choose what to order, just need a few details below...",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF4E342E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                AITextField("Prep Time (min)", prepTime.value.toString()) {
                    aiViewModel.setPrepTime(it)  // I didn't just do it.toInt() because if value is not Int then app would crash hence it.toIntOrNull() ?: 0 ensures app don't crash
                }

                AITextField("Cook Time (min)", cookTime.value.toString()) {
                    aiViewModel.setCookTime(it)
                }

                AITextField("Total Time (min)", totalTime.value.toString()) {
                    aiViewModel.setTotalTime(it)
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown("Cuisine", cuisineOptions, cuisine.value) {
                    aiViewModel.setCuisine(it)
                }

                TextDropDown("Course", courseOptions, course.value) {
                    aiViewModel.setCourse(it)
                }

                TextDropDown("Diet", dietOptions, diet.value) {
                    aiViewModel.setDiet(it)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { aiViewModel.runModel() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF57C00),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("🎯 Suggest Me Food", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text=errorMessage.value?:"", color = Color.Red)

                if (recipeName.value.isNotBlank()) {
                    Text(
                        text = "🍽️ Suggested Food: ${recipeName.value}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF4E342E)
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }

}