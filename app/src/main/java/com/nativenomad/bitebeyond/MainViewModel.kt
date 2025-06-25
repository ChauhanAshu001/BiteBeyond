package com.nativenomad.bitebeyond

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nativenomad.bitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.bitebeyond.domain.usecases.login.LoginUseCases
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
):ViewModel() {
    var startDestination by mutableStateOf(Routes.AppStartNavigation.route)
        private set
    var splashCondition by mutableStateOf(true)
    val auth = FirebaseAuth.getInstance()

    init{
        appEntryUseCases.readAppEntry().onEach{shouldStartFromSignupScreen->
            if(shouldStartFromSignupScreen){
                startDestination=Routes.SignUpNavigation.route
                if (auth.currentUser != null) {
                    startDestination = Routes.MainScreenNavigation.route
                }
            }
            else{

                startDestination=Routes.AppStartNavigation.route
            }
            delay(300)
            splashCondition=false
        }.launchIn(viewModelScope) //collect the items from the flow given by .readAppEntry() because onEach only reads the items and doesn't collects them
    }
}