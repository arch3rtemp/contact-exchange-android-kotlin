plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.navigationSafeargs)
}

android {
    namespace = "dev.arch3rtemp.contactexchange"

    compileSdk = 35

    defaultConfig {
        applicationId = "dev.arch3rtemp.contactexchangekotlin"
        minSdk = 21
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "21"
    }
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
    testImplementation(kotlin("test"))
//    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockk)
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
