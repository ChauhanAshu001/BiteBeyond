package com.nativenomad.adminbitebeyond

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.adminbitebeyond.presentation.navGraph.Routes
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

    init{
        appEntryUseCases.readAppEntry().onEach{shouldStartFromSignUp->
            if(shouldStartFromSignUp){
                startDestination=Routes.SignUpScreen.route
            }
            else{
                startDestination=Routes.AppStartNavigation.route
            }
            delay(300)
            splashCondition=false
        }.launchIn(viewModelScope)
    }
}