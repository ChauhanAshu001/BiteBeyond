package com.nativenomad.bitebeyond.di

import android.app.Application
import com.google.android.gms.auth.api.Auth
import com.nativenomad.bitebeyond.data.manager.AuthManagerImpl
import com.nativenomad.bitebeyond.data.manager.LocalUserManagerImpl
import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.domain.manager.LocalUserManager
import com.nativenomad.bitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.bitebeyond.domain.usecases.app_entry.ReadAppEntry
import com.nativenomad.bitebeyond.domain.usecases.app_entry.SaveAppEntry
import com.nativenomad.bitebeyond.domain.usecases.login.CreateAccountWithEmail
import com.nativenomad.bitebeyond.domain.usecases.login.LoginUseCases
import com.nativenomad.bitebeyond.domain.usecases.login.LoginWithEmail
import com.nativenomad.bitebeyond.domain.usecases.login.SignInWithGoogle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Dependencies {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application    //application class provides a global Context that can be accessed throughout your app, making it ideal for scenarios where you need an application-wide context.
    ): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ): AppEntryUseCases {
        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )
    }

    @Provides
    @Singleton
    fun provideAuthManager(application: Application):AuthManager{
        return AuthManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(
        authManager: AuthManager
    ):LoginUseCases{
        return LoginUseCases(
            createAccountWithEmail = CreateAccountWithEmail(authManager),
            loginWithEmail = LoginWithEmail(authManager),
            signInWithGoogle = SignInWithGoogle(authManager)
        )
    }
}