package com.nativenomad.adminbitebeyond.presentation.onboarding

sealed class OnBoardingEvent {
    object SaveAppEntry:OnBoardingEvent()
    object NavigateToSignUpScreen : OnBoardingEvent()
}