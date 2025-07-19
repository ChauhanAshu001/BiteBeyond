package com.nativenomad.bitebeyond.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
import com.nativenomad.bitebeyond.presentation.profile.components.ListCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController:NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
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
                    containerColor = colorResource(id = R.color.darkOrange)
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
            ListCard(
                title = "My Account",
                subtitle = "Edit restaurant data",
                onClick = {navController.navigate(Routes.MyAccountScreen.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ListCard(
                title = "Past Orders",
                subtitle = "Manage past orders",
                onClick = { navController.navigate(Routes.PastOrderScreen.route)}
            )
            Spacer(modifier = Modifier.height(16.dp))

//            Button(
//                onClick = {
//                    FirebaseAuth.getInstance().signOut()
//                    navController.navigate(Routes.SignInScreen.route) {
//                        popUpTo(Routes.MainScreen.route) { inclusive = true }
//                    }
//                },
//                modifier = Modifier,
////                    .fillMaxWidth()
////                    .padding(horizontal = 24.dp, vertical = 12.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = colorResource(R.color.darkOrange),
//                    contentColor = Color.White
//                ),
//                shape = RoundedCornerShape(12.dp),
//                elevation = ButtonDefaults.buttonElevation(
//                    defaultElevation = 8.dp,
//                    pressedElevation = 2.dp
//                )
//            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Logout Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate(Routes.SignInScreen.route) {
                            popUpTo(Routes.MainScreen.route) { inclusive = true }
                            }
                        }
                )
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
//            }


        }
    }
}