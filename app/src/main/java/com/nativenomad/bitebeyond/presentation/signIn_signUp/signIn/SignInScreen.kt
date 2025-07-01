@file:JvmName("SignInScreenKt")

package com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.facebook.CallbackManager
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.components.SignInFacebookButton
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn.components.SignInGoogleButton
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme

@Composable
fun SignInScreen(viewModel: SignInViewModel= hiltViewModel(),
                 onEvent:(SignInNavigationEvent)->Unit) {


    val email =viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()

    val uiState = viewModel.uiState.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading=remember{ mutableStateOf(false) }
    val callbackManager = remember { CallbackManager.Factory.create() }

    var passwordVisible by remember { mutableStateOf(false) } //used to hide and show password

    when(uiState.value){
        is SignInEvent.Error->{
            loading.value=false
            errorMessage.value="Failed"
        }
        is SignInEvent.Loading->{
            loading.value=true
            errorMessage.value=null
        }
        else->{
            loading.value=false
            errorMessage.value=null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 70.dp)
    ) {
        // Skip Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(onClick = { onEvent(SignInNavigationEvent.NavigateToHomeFromSkip) }) {
                Text("Skip", color = Color.Gray)
            }
        }
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
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text=errorMessage.value?:"", color = Color.Red)

            Button(
                onClick =  {viewModel.onSignInWithEmail()},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(R.color.white),
                    containerColor = colorResource(R.color.lightOrange)
                )

            ){
                Box{
                    AnimatedContent(targetState= loading.value){ target->
                        if(target){
                            CircularProgressIndicator(
                                color=Color.White,
                                modifier=Modifier.size(24.dp)
                            )
                        }
                        else{
                            Text("Sign In to your account")
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(32.dp))
            // Google & Facebook Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SignInGoogleButton(modifier = Modifier.weight(1f))
                SignInFacebookButton(modifier = Modifier.weight(1f), callbackManager = callbackManager)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // New User Text
            TextButton(
                onClick = { onEvent(SignInNavigationEvent.NavigateToSignUp) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("New user? Create an account", color = Color.Blue)
            }
        }


    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignInScreenPreview(modifier: Modifier = Modifier) {
    BiteBeyondTheme {
        SignInScreen(onEvent={})
    }
}
