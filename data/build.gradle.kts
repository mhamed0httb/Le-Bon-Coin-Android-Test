plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "fr.leboncoin.data"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        @Suppress("DEPRECATION")
        jvmTarget = "11"
        @Suppress("DEPRECATION")
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlinx.serialization.InternalSerializationApi"
    }

    buildFeatures {
        buildConfig = true
    }

    dependencies {
        implementation(project(":domain"))
        implementation(libs.retrofit.core)
        implementation(libs.retrofit.kotlin.serialization)
        implementation(libs.okhttp.logging)

        implementation(libs.kotlin.serialization.json)
        implementation(libs.hilt.android)
        implementation(libs.timber)
        ksp(libs.hilt.compiler)

        // Room
        implementation(libs.room.runtime)
        implementation(libs.room.ktx)
        implementation(libs.room.paging)
        implementation(libs.androidx.paging.runtime)
        ksp(libs.room.compiler)

        testImplementation(libs.junit)
        testImplementation(libs.mockk)
        testImplementation(libs.turbine)
        testImplementation(libs.kotlinx.coroutines.test)
        androidTestImplementation(libs.androidx.junit)
    }
}
