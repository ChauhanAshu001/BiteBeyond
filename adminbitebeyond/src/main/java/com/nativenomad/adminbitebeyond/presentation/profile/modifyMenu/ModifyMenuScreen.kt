package com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.common.FoodItemImage
import com.nativenomad.adminbitebeyond.presentation.common.MenuItemCard
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd.MenuAddEvent
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.menuAdd.MenuAddNavigationEvent

@Composable
fun ModifyMenuScreen(
    modifyMenuViewModel: ModifyMenuViewModel= hiltViewModel(),
    onEvent: (ModifyMenuNavigationEvent) -> Unit
) {
    val name = modifyMenuViewModel.name.collectAsState()
    val cost = modifyMenuViewModel.cost.collectAsState()
    val menu = modifyMenuViewModel.menu.collectAsState()
    val foodCategory = modifyMenuViewModel.foodCategory.collectAsState()

    val uiState = modifyMenuViewModel.uiState.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading= remember{ mutableStateOf(false) }

    when(uiState.value){
        is ModifyMenuEvents.Error->{
            loading.value=false
            errorMessage.value="Failed"
        }
        is ModifyMenuEvents.Loading->{
            loading.value=true
            errorMessage.value=null
        }
        else->{
            loading.value=false
            errorMessage.value=null
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 100.dp, bottom = 150.dp), // extra bottom padding to avoid overlap
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Add Item to Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))
                FoodItemImage(onImageSelected = {modifyMenuViewModel.setImageUri(it)})
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Food Item Name",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { modifyMenuViewModel.setName(it) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Text(
                    text = "Food Item Cost",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = cost.value,
                    onValueChange = { modifyMenuViewModel.setCost(it) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Text(
                    text = "Food Category",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = foodCategory.value,
                    onValueChange = { modifyMenuViewModel.setFoodCategory(it) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            items(menu.value) { item ->
                MenuItemCard(foodItem = item, onDeleteClick = {modifyMenuViewModel.deleteItem(it)})
            }

            item {
                Spacer(modifier = Modifier.height(100.dp)) // Bottom space before buttons
            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)      //aligns the row with respect to its parent composable
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                onClick = {
                    modifyMenuViewModel.addItem()

                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lightGreen)),
                shape = RoundedCornerShape(25.dp)
            ) {
                Box{
                    AnimatedContent(targetState = loading.value) {targetState ->
                        if(targetState){
                            CircularProgressIndicator(
                                color= Color.White,
                                modifier=Modifier.size(24.dp)
                            )
                        }
                        else{
                            Text("Add Item", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    onEvent(ModifyMenuNavigationEvent.NavigateToProfileScreen)

                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lightGreen)),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Save", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}