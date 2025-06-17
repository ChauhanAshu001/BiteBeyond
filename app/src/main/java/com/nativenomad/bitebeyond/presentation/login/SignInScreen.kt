@file:JvmName("SignInScreenKt")

package com.nativenomad.bitebeyond.presentation.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.common.FacebookButton
import com.nativenomad.bitebeyond.presentation.common.GoogleButton
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme

@Composable
fun SignInScreen() { //navController: NavController
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image Placeholder
        Image(
            painter = painterResource(id = R.drawable.burger), // Replace with your image
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(.8f),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo (Optional)
//            Image(
//                painter = painterResource(id = R.drawable.ic_logo), // Replace with your logo
//                contentDescription = "App Logo",
//                modifier = Modifier.size(100.dp).alpha(0.5f)
//            )

            Spacer(modifier = Modifier.height(24.dp))

            // Google & Facebook Buttons in One Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GoogleButton(modifier = Modifier.weight(1f))
                FacebookButton(modifier=Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Sign-In Button Below Google & Facebook
            Button(
                onClick = { /* Handle Email Sign-In */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign in with Email")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Already Have an Account Button Below Email Sign-In
            TextButton(
                onClick = { /* Navigate to Login Screen */ },
                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.Center
            ) {
                Text("Already have an account?", color = Color.Blue)
            }
        }

        // Skip Button (Top Right)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(onClick = { /* Handle Skip */ }) {
                Text("Skip", color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignInScreenPreview(modifier: Modifier = Modifier) {
    BiteBeyondTheme {
        SignInScreen()
    }
}
