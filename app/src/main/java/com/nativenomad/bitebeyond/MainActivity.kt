package com.nativenomad.bitebeyond

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.facebook.FacebookSdk
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.nativenomad.bitebeyond.presentation.navgraph.NavGraph
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()        //this isn't provided by constructor inject bcz hilt @Inject don't work for activities it only work for classes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.setClientToken("ccebd503d2f20bbb2edef55732fc8138")
        FacebookSdk.sdkInitialize(applicationContext)
        FacebookSdk.fullyInitialize()
//        //above code authenticate the app with facebook using app id. It's standard code

        //Initialize Firebase
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        installSplashScreen().apply{
            setKeepOnScreenCondition{
                viewModel.splashCondition
                //splash screen is shown until readAppEntry function don't give the value true and false and depending on that value either onBoarding screen opens or home screen opens
            }
        }
        setContent {
            BiteBeyondTheme {
                val isSystemInDarkMode= isSystemInDarkTheme()
                val systemController=rememberSystemUiController()
                SideEffect {
                    systemController.setSystemBarsColor(
                        color= Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }
                Box(modifier= Modifier.background(color= MaterialTheme.colorScheme.background)){
                    val startDestination=viewModel.startDestination
                    NavGraph(startDestination = startDestination )
                }


            }
        }
    }
}

