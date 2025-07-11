package com.nativenomad.adminbitebeyond.presentation.signin_signup.signup

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.facebook.CallbackManager
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signup.components.FacebookButton
import com.nativenomad.adminbitebeyond.presentation.signin_signup.signup.components.GoogleButton
import com.nativenomad.adminbitebeyond.ui.theme.BiteBeyondTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    viewModel: SignUpViewmodel = hiltViewModel(),
    onEvent:(SignUpNavigationEvent)->Unit
) {
    val name =viewModel.name.collectAsStateWithLifecycle()
    val email =viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()

    val uiState = viewModel.uiState.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading=remember{ mutableStateOf(false) }

    val callbackManager = remember { CallbackManager.Factory.create() }
    var passwordVisible by remember { mutableStateOf(false) } //used to hide and show password

//    val callbackManager = remember { CallbackManager.Factory.create() } //for facebook authentication
//    val facebookLoginLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        callbackManager.onActivityResult(result.resultCode, result.data)
//    }

    when(uiState.value){
        is SignUpEvent.Error->{
            loading.value=false
            errorMessage.value="Failed"
        }
        is SignUpEvent.Loading->{
            loading.value=true
            errorMessage.value=null
        }
        else->{
            loading.value=false
            errorMessage.value=null
        }
    }

    val context= LocalContext.current
    LaunchedEffect(true) {
        viewModel.navigateEvent.collectLatest {event->
            when(event){
                is SignUpNavigationEvent.NavigateToRestaurantInfoScreen->{
                    Toast.makeText(context,"Signup Successful",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //I don't need a Toast to go to signIn screen It will just go to that screen
                }
            }
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(top = 70.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Create your new",
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

            Spacer(modifier = Modifier.height(40.dp))

            // Name Field
            OutlinedTextField(
                value = name.value,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Full Name") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Name Icon") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(24.dp))
            Text(text=errorMessage.value?:"", color = Color.Red)

            // Create Account Button
            Button(
                onClick = { viewModel.onCreateAccountWithEmailClick()
                          onEvent(SignUpNavigationEvent.NavigateToRestaurantInfoScreen)},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(R.color.white),
                    containerColor = colorResource(R.color.lightGreen)

                )
            ) {
                Box{
                    AnimatedContent(targetState= loading.value){ target->
                        if(target){
                            CircularProgressIndicator(
                                color=Color.White,
                                modifier=Modifier.size(24.dp)
                            )
                        }
                        else{
                            Text("Create Account")
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(40.dp))

            // Sign in with Google
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)){
                GoogleButton(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.height(16.dp))

                // Sign in with Facebook
                FacebookButton(modifier = Modifier.weight(1f),callbackManager=callbackManager)
            }


            Spacer(modifier = Modifier.height(40.dp))

            // Already have an account? Clickable Text
            Text(
                text = "Already have an account?",
                fontSize = 16.sp,
                color = Color.Blue,
                modifier = Modifier.clickable { onEvent(SignUpNavigationEvent.NavigateToSignIn) }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenPreview() {
    BiteBeyondTheme {
        SignUpScreen(onEvent={})
    }
}