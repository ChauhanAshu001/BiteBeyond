package com.nativenomad.adminbitebeyond.di

import android.app.Application
import com.nativenomad.adminbitebeyond.data.manager.LocalUserManagerImpl
import com.nativenomad.adminbitebeyond.domain.manager.LocalUserManager
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.ReadAppEntry
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.SaveAppEntry
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
}