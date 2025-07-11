package com.nativenomad.adminbitebeyond.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.navGraph.Routes
import com.nativenomad.adminbitebeyond.presentation.profile.components.ListCard


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel(),
                  navController: NavController
) {
    val restaurantName = profileViewModel.restaurantName.collectAsState()
    val restaurantDescription=profileViewModel.restaurantDescription.collectAsState()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp), // customize as needed
                color = colorResource(id = R.color.emeraldGreen),
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp,top=20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Offers Icon",
                        tint = colorResource(id=R.color.ivory)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = restaurantName.value,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = restaurantDescription.value,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            ListCard(
                title = "My Account",
                subtitle = "Edit restaurant data",
                onClick = { navController.navigate(Routes.MyAccountScreen.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ListCard(
                title = "Past Orders",
                subtitle = "Manage past orders",
                onClick = { /* Navigate or handle click */ }
            )
            Spacer(modifier = Modifier.height(16.dp))

            ListCard(
                title = "Modify restaurant's menu",
                subtitle = "Add or delete menu items",
                onClick = { navController.navigate(Routes.ModifyMenuScreen.route) }
            )
        }
    }
}