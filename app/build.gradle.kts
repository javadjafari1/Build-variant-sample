plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.buildvariantssample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.buildvariantssample"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        this.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        create("staging") {
            initWith(buildTypes["release"])
            isDebuggable = true
            applicationIdSuffix = ".staging"
        }
    }
    productFlavors {
        flavorDimensions.addAll(listOf("source", "store"))

        create("mock") {
            dimension = flavorDimensionList[0]
        }
        create("live") {
            dimension = flavorDimensionList[0]
        }
        create("playStore") {
            dimension = flavorDimensionList[1]
        }
        create("cafeBazaar") {
            dimension = flavorDimensionList[1]
        }

//        Way 1
//        androidComponents {
//            val mockReleaseSelector = selector()
//                .withFlavor(Pair("source", "mock"))
//                .withBuildType("release")
//            beforeVariants(mockReleaseSelector) {
//                it.enable = false
//            }
//
//            val mockStagingSelector = selector()
//                .withFlavor(Pair("source", "mock"))
//                .withBuildType("staging")
//            beforeVariants(mockStagingSelector) {
//                it.enable = false
//            }
//        }


//      Way 2
        androidComponents {
            beforeVariants {
                if (
                    it.name == "mockPlayStoreDebug" ||
                    it.name == "livePlayStoreDebug" ||
                    it.name == "mockPlayStoreRelease" ||
                    it.name == "mockCafeBazaarRelease" ||
                    it.name == "mockPlayStoreStaging" ||
                    it.name == "mockCafeBazaarStaging" ||
                    it.name == "liveCafeBazaarStaging"
                ) {
                    it.enable = false
                }
            }
        }

//        way 3
//        androidComponents {
//            beforeVariants {
//                if (
//                    it.productFlavors.contains("source" to "mock") &&
//                    (it.buildType == "release" || it.buildType == "staging")
//                ) {
//                    it.enable = false
//                }
//            }
//        }
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}