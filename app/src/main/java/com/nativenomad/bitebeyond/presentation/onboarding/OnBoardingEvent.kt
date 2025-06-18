package com.nativenomad.bitebeyond.presentation.onboarding

sealed class OnBoardingEvent {
    object SaveAppEntry:OnBoardingEvent()
    data object NavigateToSignUp : OnBoardingEvent()
}