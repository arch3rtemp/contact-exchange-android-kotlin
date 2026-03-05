plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.navigationSafeargs)
}

android {
    namespace = "dev.arch3rtemp.contactexchange"

    compileSdk = 36

    defaultConfig {
        applicationId = "dev.arch3rtemp.contactexchangekotlin"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        isCoreLibraryDesugaringEnabled = true
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {

    implementation(project(":core:ui"))
    coreLibraryDesugaring(libs.desugar)

    // Jetbrains
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)

    // Jetpack
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.fragment.ktx)
    implementation(libs.core.splashscreen)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Google
    implementation(libs.material)
    implementation(libs.play.services.code.scanner)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Library for QR
    implementation(libs.qrgenerator)

    // Library for swipe recyclerview
    implementation(libs.swipereveallayout)

    // Testing
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockk)
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.espresso.core)
}
