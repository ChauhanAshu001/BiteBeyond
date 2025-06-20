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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.facebook.CallbackManager
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.common.FacebookButton
import com.nativenomad.bitebeyond.presentation.common.GoogleButton
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme

@Composable
fun SignInScreen() {
    val callbackManager = remember { CallbackManager.Factory.create() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 70.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bold Heading: "Login to your account"
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Login to your",
                    color = Color.Black,
                    fontSize = 28.sp
                )
                Text(
                    text = "account",
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Email Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* update email */ },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* update password */ },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick =  {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(R.color.white),
                    containerColor = colorResource(R.color.lightOrange)
                )

            ){
                Text("Sign In to your account")
            }

            Spacer(modifier = Modifier.height(32.dp))
            // Google & Facebook Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GoogleButton(modifier = Modifier.weight(1f))
                FacebookButton(modifier = Modifier.weight(1f), callbackManager = callbackManager)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // New User Text
            TextButton(
                onClick = { /* Navigate to sign-up */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("New user? Create an account", color = Color.Blue)
            }
        }

        // Skip Button
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
