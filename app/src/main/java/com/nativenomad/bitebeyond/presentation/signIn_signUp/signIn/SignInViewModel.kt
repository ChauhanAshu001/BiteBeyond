package com.nativenomad.bitebeyond.presentation.signIn_signUp.signIn

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.facebook.CallbackManager
import com.nativenomad.bitebeyond.domain.usecases.login.LoginUseCases
import com.nativenomad.bitebeyond.models.AuthResponse
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp.SignUpEvent
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp.SignUpNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SignInViewModel@Inject constructor(
    private val loginUseCases: LoginUseCases
):ViewModel() {
    private val _uiState= MutableStateFlow<SignInEvent>(SignInEvent.Nothing)
    val uiState=_uiState.asStateFlow()

    private val _navigateEvent= MutableSharedFlow<SignInNavigationEvent>()  // we made in shared flow because we didn't want this variable to have a pre stored value because then the ui will reach that screen whose value is pre stored
    val navigateEvent=_navigateEvent.asSharedFlow()

    private val _email= MutableStateFlow<String>("")
    val email=_email.asStateFlow()
    private val _password= MutableStateFlow<String>("")
    val password=_password.asStateFlow()

    fun onEmailChange(email:String){
        _email.value=email
    }
    fun onPasswordChange(password:String){
        _password.value=password
    }

    fun onSignInWithFacebookClick(activity: ComponentActivity,
                                  callbackManager: CallbackManager
    ){
        _uiState.value= SignInEvent.Loading
        loginUseCases.signInWithFacebook(activity,callbackManager).onEach{response->
            if(response is AuthResponse.Success) {
                _uiState.value= SignInEvent.Success
                _navigateEvent.emit(SignInNavigationEvent.NavigateToHome)
            }
            else{
                _uiState.value= SignInEvent.Error
            }
        }.launchIn(viewModelScope)
    }

    fun onSignInWithGoogleClick(){
        _uiState.value= SignInEvent.Loading
        loginUseCases.signInWithGoogle().onEach{response->
            if(response is AuthResponse.Success) {
                _uiState.value= SignInEvent.Success
                _navigateEvent.emit(SignInNavigationEvent.NavigateToHome)
            }
            else{
                _uiState.value= SignInEvent.Error
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SignInNavigationEvent){
        when(event){
            is SignInNavigationEvent.NavigateToSignUp->{
                viewModelScope.launch{
                    _navigateEvent.emit(SignInNavigationEvent.NavigateToSignUp)
                }
            }
            is SignInNavigationEvent.NavigateToHome->{
                viewModelScope.launch {
                    _navigateEvent.emit(SignInNavigationEvent.NavigateToHome)
                }
            }
            is SignInNavigationEvent.NavigateToHomeFromSkip->{
                viewModelScope.launch {
                    _navigateEvent.emit(SignInNavigationEvent.NavigateToHomeFromSkip)
                }
            }
        }
    }

    fun onSignInWithEmail(){
        _uiState.value= SignInEvent.Loading
        loginUseCases.loginWithEmail(email=email.value,password=password.value).onEach { response->
            if(response is AuthResponse.Success) {
                _uiState.value= SignInEvent.Success
                _navigateEvent.emit(SignInNavigationEvent.NavigateToHome)
            }
            else {
                _uiState.value=SignInEvent.Error
            }
        }.launchIn(viewModelScope)
    }
}