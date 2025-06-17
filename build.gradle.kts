plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false // Assuming you still use Compose
    alias(libs.plugins.hilt.android) apply false   // Hilt Gradle plugin
    id("org.jetbrains.kotlin.kapt") version libs.versions.kotlin.get() apply false

    //firebase
    id("com.google.gms.google-services") version "4.4.2" apply false
}
