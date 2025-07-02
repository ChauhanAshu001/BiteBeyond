package com.nativenomad.bitebeyond.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.bitebeyond.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {
    private val _navigationEvent = MutableSharedFlow<OnBoardingEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()
    fun onEvent(event:OnBoardingEvent){
        when(event){
            is OnBoardingEvent.SaveAppEntry->{
                saveAppEntry()
            }
            is OnBoardingEvent.NavigateToSignUpScreen -> {
                viewModelScope.launch {
                    _navigationEvent.emit(OnBoardingEvent.NavigateToSignUpScreen)
                }
            }
        }
    }

    private fun saveAppEntry() {

        viewModelScope.launch{
            appEntryUseCases.saveAppEntry()    //save entry is a suspend function hence we can only call it inside coroutine or another suspend function
            //operator function means we can call this function by the name of its class
        }
    }
}