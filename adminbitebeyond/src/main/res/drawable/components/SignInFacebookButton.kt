package com.nativenomad.adminbitebeyond.presentation.signin_signup.signin.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.CallbackManager
import com.nativenomad.adminbitebeyond.MainActivity
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signin.SignInViewModel
import com.nativenomad.bitebeyond.MainActivity
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.SignInViewModel

@Composable
fun SignInFacebookButton(modifier: Modifier,
                         viewModel: SignInViewModel = hiltViewModel(),
                         callbackManager: CallbackManager
) {
    val context = LocalContext.current
//    val activity = context as Activity
    val activity = context as MainActivity

    Button(
        onClick = {
            viewModel.onSignInWithFacebookClick(activity, callbackManager)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1877F2),
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.facebook), // Replace with Facebook icon
            contentDescription = "Facebook Icon",
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Facebook", color = Color.White)
    }
}