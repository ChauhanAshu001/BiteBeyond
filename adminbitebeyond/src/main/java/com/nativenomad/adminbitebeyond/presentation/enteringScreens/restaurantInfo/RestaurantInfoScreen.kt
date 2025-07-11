package com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo.components.RestaurantImage

@Composable
fun RestaurantInfoScreen(restaurantInfoViewModel: RestaurantInfoViewModel= hiltViewModel(),
                         onEvent:(RestaurantInfoNavigationEvent)->Unit
) {
    val restaurantName = restaurantInfoViewModel.restaurantName.collectAsState()
    val restaurantDescription = restaurantInfoViewModel.restaurantDescription.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(bottom = 100.dp,top=100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Enter Restaurant Details",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        RestaurantImage()

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Restaurant Name",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = restaurantName.value,
            onValueChange = { restaurantInfoViewModel.setRestaurantName(it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Text(
            text = "Restaurant Description",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = restaurantDescription.value,
            onValueChange = { restaurantInfoViewModel.setRestaurantDescription(it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Button(
            onClick = {
                restaurantInfoViewModel.setRestaurantData()
                onEvent(RestaurantInfoNavigationEvent.NavigateToLocationInfoScreen)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id= R.color.lightGreen)),
            shape = RoundedCornerShape(25.dp)
        ){
            Text("Add Details", color = Color.White, fontWeight = FontWeight.Bold)
        }

    }
}