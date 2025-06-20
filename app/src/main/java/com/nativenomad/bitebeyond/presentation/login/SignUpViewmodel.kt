package com.nativenomad.bitebeyond.presentation.login

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.domain.usecases.login.LoginUseCases
import com.nativenomad.bitebeyond.models.AuthResponse
import com.nativenomad.bitebeyond.presentation.navgraph.NavGraph
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
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
class SignUpViewmodel@Inject constructor(
    private val loginUseCases: LoginUseCases
):ViewModel() {

    private val _uiState= MutableStateFlow<SignUpEvent>(SignUpEvent.Nothing)
    val uiState=_uiState.asStateFlow()
    /*
    The .asStateFlow() function in Kotlin is used to convert a MutableStateFlow into a read-only StateFlow.
    This ensures that the state can be observed but not modified externally, maintaining encapsulation.
    */

    private val _navigateEvent= MutableSharedFlow<SignUpNavigationEvent>()  // we made in shared flow because we didn't want this variable to have a pre stored value because then the ui will reach that screen whose value is pre stored
    val navigateEvent=_navigateEvent.asSharedFlow()

    private val _email= MutableStateFlow<String>("")
    val email=_email.asStateFlow()
    private val _password= MutableStateFlow<String>("")
    val password=_password.asStateFlow()
    private val _name= MutableStateFlow<String>("")
    val name=_name.asStateFlow()

    fun onEmailChange(email:String){
        _email.value=email
    }
    fun onPasswordChange(password:String){
        _password.value=password
    }
    fun onNameChange(name:String){
        _name.value=name
    }

     fun onCreateAccountWithEmailClick(){
        _uiState.value=SignUpEvent.Loading
        loginUseCases.createAccountWithEmail(email=email.value,password=password.value).onEach{response->
            if(response is AuthResponse.Success) {
                _uiState.value=SignUpEvent.Success
                _navigateEvent.emit(SignUpNavigationEvent.NavigateToHome)
            }
        }.launchIn(viewModelScope)
    }

    fun onSignInWithGoogleClick(){
        _uiState.value=SignUpEvent.Loading
        loginUseCases.signInWithGoogle().onEach{response->
            if(response is AuthResponse.Success) {
                _uiState.value=SignUpEvent.Success
                _navigateEvent.emit(SignUpNavigationEvent.NavigateToHome)
            }
            else{
                _uiState.value=SignUpEvent.Error
            }
        }.launchIn(viewModelScope)
    }

    fun onSignInWithFacebookClick(activity: ComponentActivity,
                                  callbackManager: CallbackManager
    ){
        _uiState.value=SignUpEvent.Loading
        loginUseCases.signInWithFacebook(activity,callbackManager).onEach{response->
            if(response is AuthResponse.Success) {
                _uiState.value=SignUpEvent.Success
                _navigateEvent.emit(SignUpNavigationEvent.NavigateToHome)
            }
            else{
                _uiState.value=SignUpEvent.Error
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(event:SignUpNavigationEvent){
        when(event){
            is SignUpNavigationEvent.NavigateToLogin->{
                viewModelScope.launch{
                    _navigateEvent.emit(SignUpNavigationEvent.NavigateToLogin)
                }
            }
            is SignUpNavigationEvent.NavigateToHome->{
                viewModelScope.launch {
                    _navigateEvent.emit(SignUpNavigationEvent.NavigateToHome)
                }
            }
        }
    }

}