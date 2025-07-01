package com.nativenomad.bitebeyond.di

import android.app.Application
import androidx.room.Room
import com.nativenomad.bitebeyond.data.local.CartDao
import com.nativenomad.bitebeyond.data.local.CartDatabase
import com.nativenomad.bitebeyond.data.manager.AuthManagerImpl
import com.nativenomad.bitebeyond.data.manager.LocalUserManagerImpl
import com.nativenomad.bitebeyond.data.manager.PermissionManagerImpl
import com.nativenomad.bitebeyond.data.repository.CartRepositoryImpl
import com.nativenomad.bitebeyond.data.repository.DatabaseOpImpl
import com.nativenomad.bitebeyond.data.repository.ProfileDataRepositoryImpl
import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.domain.manager.LocalUserManager
import com.nativenomad.bitebeyond.domain.manager.PermissionManager
import com.nativenomad.bitebeyond.domain.repository.CartRepository
import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.domain.repository.ProfileDataRepository
import com.nativenomad.bitebeyond.domain.usecases.app_entry.AppEntryUseCases
import com.nativenomad.bitebeyond.domain.usecases.app_entry.ReadAppEntry
import com.nativenomad.bitebeyond.domain.usecases.app_entry.SaveAppEntry
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.GetCategories
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.GetMenu
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.GetOffers
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.GetPromoCodeRestaurantMap
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.GetRestaurants
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.GetUserData
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.SaveUserData
import com.nativenomad.bitebeyond.domain.usecases.login.CreateAccountWithEmail
import com.nativenomad.bitebeyond.domain.usecases.login.LoginUseCases
import com.nativenomad.bitebeyond.domain.usecases.login.LoginWithEmail
import com.nativenomad.bitebeyond.domain.usecases.login.SignInWithFacebook
import com.nativenomad.bitebeyond.domain.usecases.login.SignInWithGoogle
import com.nativenomad.bitebeyond.domain.usecases.permissions.GetUserLocation
import com.nativenomad.bitebeyond.domain.usecases.permissions.PermissionUseCases
import com.nativenomad.bitebeyond.remote.FreeImageApi
import com.nativenomad.bitebeyond.utils.CalculateDistanceClass
import com.nativenomad.bitebeyond.utils.GetUserLocationNameClass
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
            signInWithGoogle = SignInWithGoogle(authManager) ,
            signInWithFacebook= SignInWithFacebook(authManager)
        )
    }

    @Provides
    @Singleton
    fun provideDatabaseOp(application: Application):DatabaseOp{
        return DatabaseOpImpl(application)
    }

    @Provides
    @Singleton
    fun provideDatabaseOpUseCases(
        databaseOp: DatabaseOp
    ):DatabaseOpUseCases{
        return DatabaseOpUseCases(
            getCategories = GetCategories(databaseOp),
            getRestaurants = GetRestaurants(databaseOp),
            getMenu= GetMenu(databaseOp),
            getOffers = GetOffers(databaseOp),
            getPromoCodeRestaurantMap = GetPromoCodeRestaurantMap(databaseOp),
            saveUserData = SaveUserData(databaseOp),
            getUserData = GetUserData(databaseOp)
        )
    }


    @Provides
    @Singleton
    fun providePermissionManager(application: Application):PermissionManager{
        return PermissionManagerImpl(application)
    }

    @Provides
    @Singleton
    fun providePermissionsUseCases(permissionManager: PermissionManager):PermissionUseCases{
        return PermissionUseCases(
            getUserLocation = GetUserLocation(permissionManager)
        )
    }

    @Provides
    @Singleton
    fun provideCalculateDistance():CalculateDistanceClass{
        return CalculateDistanceClass()

    }
    @Provides
    @Singleton
    fun provideGetUserLocationNameClass():GetUserLocationNameClass{
        return GetUserLocationNameClass()
    }

    @Provides
    @Singleton
    fun provideCartRepository(application: Application,cartDao: CartDao):CartRepository{
        return CartRepositoryImpl(
            application = application,
            cartDao = cartDao

        )
    }
    /*I didn't use that useCases way for cart because it had stateflows like final amount,cartItems etc to manage which were only in CartrepositoryImpl
    so we can't just inject a cartRepositoryUse cases into cartViewModel
    */

    @Provides
    @Singleton
    fun provideCartDatabase(application: Application):CartDatabase{
        return Room.databaseBuilder(
            context = application,
            klass=CartDatabase::class.java,
            name="Cart_Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(cartDatabase: CartDatabase):CartDao{
        return cartDatabase.cartDao()
    }

    @Provides
    @Singleton
    fun provideFreeImageApi():FreeImageApi{
        return Retrofit.Builder()
            .baseUrl("https://freeimage.host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FreeImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileDataRepository(databaseOpUseCases: DatabaseOpUseCases):ProfileDataRepository{
        return ProfileDataRepositoryImpl(databaseOpUseCases)
    }

}