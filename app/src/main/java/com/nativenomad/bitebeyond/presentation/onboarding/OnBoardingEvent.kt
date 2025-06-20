package com.nativenomad.bitebeyond.presentation.onboarding

sealed class OnBoardingEvent {
    object SaveAppEntry:OnBoardingEvent()
    object NavigateToSignUp : OnBoardingEvent()
}