package com.nativenomad.adminbitebeyond.presentation.signin_signup.signin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.SignInViewModel

@Composable
fun SignInGoogleButton(modifier: Modifier,
                 viewModel: SignInViewModel = hiltViewModel()
) {
    Button(
        onClick = viewModel::onSignInWithGoogleClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google), // Replace with Google icon
            contentDescription = "Google Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Google", color = Color.Black)
    }

}
