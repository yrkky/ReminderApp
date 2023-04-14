plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.syrjakoyrjanai.core.data"
    compileSdk = sdk.compile

    defaultConfig {
        minSdk = sdk.min
        targetSdk = sdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":core-domain"))
    implementation(project(":core-database"))

    implementation(androidx.core.ktx)

    implementation(google.dagger.hilt.android)
    kapt(google.dagger.hilt.compiler)

    implementation(kotlinx.coroutines.android.android)
    implementation(kotlinx.coroutines.core.core)

    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.core)
}

kapt {
    correctErrorTypes = true
}