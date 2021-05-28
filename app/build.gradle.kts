plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    aaptOptions {
        // Ignore files from assets for not including them in final APK
        // TODO remove this for no encrypt with C++
        ignoreAssetsPattern = "client-cert.p12:master-cacert.pem"
    }
    compileSdkVersion(30)
    buildToolsVersion = "29.0.3"
    flavorDimensions("default")

    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.mo2o.template"
        minSdkVersion(24)
        targetSdkVersion(30)
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // TODO Config environment vars in home gradle.properties (~/.gradle/gradle.properties)
    signingConfigs {
        create("release") {
        }
        getByName("debug") {}
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField(
                "okhttp3.logging.HttpLoggingInterceptor.Level",
                "LEVEL_LOGS",
                "okhttp3.logging.HttpLoggingInterceptor.Level.NONE"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            buildConfigField(
                "okhttp3.logging.HttpLoggingInterceptor.Level",
                "LEVEL_LOGS",
                "okhttp3.logging.HttpLoggingInterceptor.Level.BODY"
            )
        }
    }
    // TODO Set urls for each environment
    productFlavors {
        create("pro") {
            buildConfigField("String", "HOST", "\"https://quiet-stone-2094.herokuapp.com/\"")
        }
    }


    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf(
            "-Xopt-in=kotlin.time.ExperimentalTime",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }

}

kapt {
    correctErrorTypes = true
}
dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk7", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")

    // Android jetpack
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.core:core-ktx:1.3.2")

    // Support
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    // Dagger
    implementation("com.google.dagger:hilt-android:2.28.3-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.28.3-alpha")
    // Retrofit OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.3")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")


    implementation("androidx.lifecycle:lifecycle-runtime:2.3.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    // Multidex
    implementation("androidx.multidex:multidex:2.0.1")
    // Required for local unit tests
    testImplementation("junit:junit:4.13.2")
    // Test mockito
    testImplementation("org.mockito:mockito-core:3.7.7")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    // Test MockWebServer
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    // Test Turbine
    testImplementation("app.cash.turbine:turbine:0.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
}
