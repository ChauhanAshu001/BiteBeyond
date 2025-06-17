package com.nativenomad.bitebeyond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nativenomad.bitebeyond.presentation.login.SignUpScreen
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            BiteBeyondTheme {
                SignUpScreen()
            }
        }
    }
}

