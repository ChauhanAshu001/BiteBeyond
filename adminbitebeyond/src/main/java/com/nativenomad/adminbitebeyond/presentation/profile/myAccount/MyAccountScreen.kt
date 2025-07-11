package com.nativenomad.adminbitebeyond.presentation.profile.myAccount

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.adminbitebeyond.presentation.profile.ProfileEvents
import com.nativenomad.adminbitebeyond.presentation.profile.components.myAccount.ProfileRestaurantImage
import com.nativenomad.adminbitebeyond.presentation.profile.components.myAccount.ProfileTextField

@Composable
fun MyAccountScreen(myAccountViewModel: MyAccountViewModel = hiltViewModel()) {
    val restaurantName by myAccountViewModel.restaurantName.collectAsState()
    val restaurantDescription by myAccountViewModel.restaurantDescription.collectAsState()
    val address by myAccountViewModel.address.collectAsState()
    val pincode by myAccountViewModel.pincode.collectAsState()
    val state by myAccountViewModel.state.collectAsState()
    val country by myAccountViewModel.country.collectAsState()
    val imageUrl by myAccountViewModel.imageUrl.collectAsState()
    val uiState by myAccountViewModel.uiState.collectAsState()

    var nameInput by remember { mutableStateOf(restaurantName) }
    var descriptionInput by remember { mutableStateOf(restaurantDescription) }
    var addressInput by remember { mutableStateOf(address) }
    var pincodeInput by remember { mutableStateOf(pincode) }
    var stateInput by remember { mutableStateOf(state) }
    var countryInput by remember { mutableStateOf(country) }

    val loading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    when (uiState) {
        is ProfileEvents.Error -> {
            loading.value = false
            errorMessage.value = "Failed"
        }

        is ProfileEvents.Loading -> {
            loading.value = true
            errorMessage.value = null
        }

        else -> {
            loading.value = false
            errorMessage.value = null
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Fixed image at the top with padding from top
        Box(modifier = Modifier.padding(top = 50.dp)) {
            ProfileRestaurantImage(
                imageUrl = imageUrl,
                onImageSelected = { myAccountViewModel.setImageUri(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn for rest of the content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                ProfileTextField(label = "Restaurant Name", value = restaurantName) {
                    nameInput = it
                    myAccountViewModel.setRestaurantName(it)
                }
            }

            item {
                ProfileTextField(label = "Description", value = restaurantDescription) {
                    descriptionInput = it
                    myAccountViewModel.setRestaurantDescription(it)
                }
            }

            item {
                ProfileTextField(label = "Address", value = address) {
                    addressInput = it
                    myAccountViewModel.setAddress(it)
                }
            }

            item {
                ProfileTextField(label = "Pincode", value = pincode) {
                    pincodeInput = it
                    myAccountViewModel.setPincode(it)
                }
            }

            item {
                ProfileTextField(label = "State", value = state) {
                    stateInput = it
                    myAccountViewModel.setState(it)
                }
            }

            item {
                ProfileTextField(label = "Country", value = country) {
                    countryInput = it
                    myAccountViewModel.setCountry(it)
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = errorMessage.value ?: "", color = Color.Red)
            }

            item {
                Button(
                    onClick = { myAccountViewModel.saveProfile() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box {
                        AnimatedContent(targetState = loading.value) { target ->
                            if (target) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text("Save Profile")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
