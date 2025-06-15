plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.learningapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.learningapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.navigation:navigation-runtime:2.4.0")
    implementation("androidx.navigation:navigation-fragment:2.4.0")
    implementation("androidx.navigation:navigation-ui:2.4.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.activity:activity-ktx:1.3.0")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))

    // Firebase Analytics
    implementation("com.google.firebase:firebase-analytics")

    // 🔥 Firebase Firestore
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)


    // Testing
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

