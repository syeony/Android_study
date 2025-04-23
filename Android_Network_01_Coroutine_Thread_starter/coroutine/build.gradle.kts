plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android { 
    namespace = "com.ssafy.coroutine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.coroutine"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    viewBinding {
        enable = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // coroutine.   core-ktx에 포함되어 있어서 추가하지 않아도 된다.
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
}
