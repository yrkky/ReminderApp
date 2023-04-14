plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.syrjakojyrjanai.reminderapp"
    compileSdk = sdk.compile

    defaultConfig {
        applicationId = "com.syrjakojyrjanai.reminderapp"

        minSdk = sdk.min
        targetSdk = sdk.target
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core-domain"))
    implementation(project(":core-database"))
    implementation(project(":core-data"))

    implementation(androidx.core.ktx)
    implementation(androidx.compose.ui.ui)
    implementation(androidx.compose.material.material)
    implementation(androidx.compose.material.extended)
    implementation(androidx.compose.ui.preview)
    implementation(androidx.lifecycle.runtime)
    implementation(androidx.lifecycle.compose)
    implementation(androidx.navigation.compose)
    implementation(androidx.activity.activity_compose)
    implementation(androidx.constraintlayout.compose)

    implementation(androidx.appcompat.app.appcompatactivity)
    implementation(google.dagger.hilt.android)
    kapt(google.dagger.hilt.compiler)

    implementation(androidx.navigation.hilt.compose)

    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.core)
    androidTestImplementation(androidx.compose.ui.ui_test_junit)
    debugImplementation(androidx.compose.ui.ui_tooling)
    debugImplementation(androidx.compose.ui.ui_test_manifest)

    implementation(google.accompanist.insets)
    implementation(kotlinx.coroutines.android.android)
    implementation(androidx.constraintlayout.compose)
    implementation(androidx.compose.foundation.foundation)

    implementation(androidx.work.runtime_ktx)
    implementation(androidx.work.rxjava2)
    implementation(androidx.work.gcm)
    androidTestImplementation(androidx.work.testing)
    implementation(androidx.work.multiprocess)

}

kapt {
    correctErrorTypes = true
}