package com.nativenomad.adminbitebeyond.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.navGraph.Routes
import com.nativenomad.adminbitebeyond.presentation.profile.components.ListCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel(),
                  navController: NavController
) {
    val restaurantName = profileViewModel.restaurantName.collectAsState()
    val restaurantDescription=profileViewModel.restaurantDescription.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = restaurantName.value.uppercase(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color= Color.Gray,
                                offset = Offset(2f, 2f),  //tells direction and distance of shadow from text, (+,+) means right and down, (+,-) means right and top
                                blurRadius = 4f  //controls the softness of shadow's edges
                            )
                        ),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Text(
                text = restaurantDescription.value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color= Color.Gray,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f  //controls the softness of shadow's edges
                        )
                ),
                color = colorResource(R.color.black)
            )
            Spacer(modifier = Modifier.height(16.dp))

            ListCard(
                title = "My Account",
                subtitle = "Edit restaurant data",
                onClick = { navController.navigate(Routes.MyAccountScreen.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ListCard(
                title = "Past Orders",
                subtitle = "Manage past orders",
                onClick = { navController.navigate(Routes.PastOrderScreen.route) }
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