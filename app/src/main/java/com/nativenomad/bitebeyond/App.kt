package com.nativenomad.bitebeyond


import android.app.Application
import com.google.firebase.FirebaseApp
import com.razorpay.Checkout
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
