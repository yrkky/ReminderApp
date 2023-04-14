plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.syrjakoyrjanai.core.domain"
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

    implementation(androidx.core.ktx)
    implementation(kotlinx.coroutines.android.android)

    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.core)
}
