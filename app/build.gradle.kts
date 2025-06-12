//import androidx.glance.appwidget.compose
//import androidx.navigation.compose.navigation


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // Assuming you still use Compose

    id("kotlin-kapt") // Apply the Kapt plugin
    alias(libs.plugins.hilt.android) // Hilt Gradle plugin

}

android {
    namespace = "com.nativenomad.bitebeyond"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nativenomad.bitebeyond"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

//    kapt {
//        correctErrorTypes = true
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dagger Hilt - Using Kapt
    implementation(libs.hilt.android) // Hilt runtime
    kapt(libs.hilt.compiler)          // Hilt Kapt compiler for main source set


    // Hilt Integration with Jetpack Compose Navigation
    implementation(libs.androidx.hilt.navigation.compose)

    //SplashAPI
    implementation(libs.androidx.core.splashscreen)

    //Compose Navigation
    implementation(libs.androidx.navigation.compose)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif) // Optional, for GIF support
    implementation(libs.coil.svg) // Optional, for SVG support

    //datastore
    implementation(libs.androidx.datastore.preferences) // For Preferences DataStore
    implementation(libs.androidx.datastore.core)    // For Proto DataStore

    //composition
    implementation(libs.androidx.foundation)

    //accompanist system ui controller
    implementation(libs.accompanist.systemuicontroller)

    //paging 3
    implementation(libs.androidx.paging.runtime) // For Java/Kotlin projects
    implementation(libs.androidx.paging.compose) // For Jetpack Compose support (optional)

    //room - Using Kapt
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)       // Room Kapt compiler
}