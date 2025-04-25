plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // 1) Enable KAPT
    kotlin("kapt")

    // 2) Apply the Hilt Gradle plugin
    id("com.google.dagger.hilt.android")
}
hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.example.continentaltest"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.continentaltest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
}

configurations.all {
    exclude(group = "com.android.support", module = "multidex")
}

dependencies {
    implementation("com.continental.kaas:library:1.3.2") {
        exclude(group = "com.guardsquare", module = "dexguard-runtime")
    }

    // 3) Hilt core
    implementation("com.google.dagger:hilt-android:2.56.2")
    implementation(libs.androidx.hilt.work)
    kapt("com.google.dagger:hilt-compiler:2.56.2")

    // your other deps...
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
}