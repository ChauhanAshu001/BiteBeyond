package com.nativenomad.adminbitebeyond.di

import android.app.Application
import androidx.room.Room
import com.nativenomad.adminbitebeyond.data.local.RestaurantDao
import com.nativenomad.adminbitebeyond.data.local.RestaurantDatabase
import com.nativenomad.adminbitebeyond.data.manager.AuthManagerImpl
import com.nativenomad.adminbitebeyond.data.manager.LocalUserManagerImpl
import com.nativenomad.adminbitebeyond.data.repository.DatabaseOpImpl
import com.nativenomad.adminbitebeyond.data.repository.RestaurantDataRepoImpl
import com.nativenomad.adminbitebeyond.data.repository.RestaurantMenuRepoImpl
import com.nativenomad.adminbitebeyond.domain.manager.AuthManager
import com.nativenomad.adminbitebeyond.domain.manager.LocalUserManager
import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantMenuRepo
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.ReadAppEntry
import com.nativenomad.adminbitebeyond.domain.usecases.app_entry.SaveAppEntry
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.AddOffer
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.DeleteOffer
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.GetAllOffers
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.GetMyOffers
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.RemoveMenuItem
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.AddMenuItem
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.GetFullMenu
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.SaveCategoryGlobally
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.SaveRestaurantData
import com.nativenomad.adminbitebeyond.domain.usecases.login.CreateAccountWithEmail
import com.nativenomad.adminbitebeyond.domain.usecases.login.LoginUseCases
import com.nativenomad.adminbitebeyond.domain.usecases.login.LoginWithEmail
import com.nativenomad.adminbitebeyond.domain.usecases.login.SignInWithFacebook
import com.nativenomad.adminbitebeyond.domain.usecases.login.SignInWithGoogle
import com.nativenomad.admnibitebeyond.remote.FreeImageApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideAuthManager(application: Application): AuthManager {
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
            signInWithGoogle = SignInWithGoogle(authManager) ,
            signInWithFacebook= SignInWithFacebook(authManager)
        )
    }

    @Provides
    @Singleton
    fun provideRestaurantDataRepo(application: Application,
                                  restaurantDao: RestaurantDao,
                                  databaseOpUseCases: DatabaseOpUseCases
                                  ):RestaurantDataRepo{
        return RestaurantDataRepoImpl(application,restaurantDao,databaseOpUseCases)
    }

    @Provides
    @Singleton
    fun provideRestaurantMenuRepo( databaseOpUseCases: DatabaseOpUseCases):RestaurantMenuRepo{
        return RestaurantMenuRepoImpl(databaseOpUseCases)
    }

    @Provides
    @Singleton
    fun provideFreeImageApi(): FreeImageApi {
        return Retrofit.Builder()
            .baseUrl("https://freeimage.host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FreeImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRestaurantDatabase(application: Application):RestaurantDatabase{
        return Room.databaseBuilder(
            context = application,
            klass=RestaurantDatabase::class.java,
            name="restaurant_Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRestaurantDao(restaurantDatabase: RestaurantDatabase):RestaurantDao{
        return restaurantDatabase.restaurantDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseOp(): DatabaseOp {
        return DatabaseOpImpl()
    }
    @Provides
    @Singleton
    fun provideDatabaseOpUseCases(databaseOp: DatabaseOp):DatabaseOpUseCases{
        return DatabaseOpUseCases(
            saveRestaurantData = SaveRestaurantData(databaseOp),
            addMenuItem = AddMenuItem(databaseOp),
            removeMenuItem = RemoveMenuItem(databaseOp),
            getAllOffers = GetAllOffers(databaseOp),
            getMyOffers = GetMyOffers(databaseOp),
            addOffer = AddOffer(databaseOp),
            deleteOffer = DeleteOffer(databaseOp),
            saveCategoriesGlobally = SaveCategoryGlobally(databaseOp),
            getFullMenu = GetFullMenu(databaseOp)
        )
    }
}