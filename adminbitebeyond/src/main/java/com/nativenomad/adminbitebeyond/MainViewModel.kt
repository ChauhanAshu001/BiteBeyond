package com.nativenomad.adminbitebeyond

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.adminbitebeyond.presentation.navGraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val restaurantDataRepo: RestaurantDataRepo
):ViewModel() {
    var startDestination by mutableStateOf(Routes.OnBoardingScreen.route)
        private set
    var splashCondition by mutableStateOf(true)
    val auth = FirebaseAuth.getInstance()

    init{
        appEntryUseCases.readAppEntry().onEach{shouldStartFromSignUp->
            if(shouldStartFromSignUp){
                startDestination=Routes.SignUpScreen.route
                if(auth.currentUser!=null && restaurantDataRepo.restaurantName.value!=""){
                    startDestination=Routes.MainScreen.route
                }
                else if(auth.currentUser!=null){
                    startDestination=Routes.RestaurantInfoScreen.route
                }
            }
            else{
                startDestination=Routes.OnBoardingScreen.route
            }
            delay(300)
            splashCondition=false
        }.launchIn(viewModelScope)
    }
}