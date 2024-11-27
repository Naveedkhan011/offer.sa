plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.realm.plugin)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp) // OkHttp engine for Android
        }
        iosMain.dependencies {
            //Dawin ktor engine for iOS
            implementation(libs.ktor.client.darwin) // Native iOS engine
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation (libs.navigation.compose)
            implementation(libs.kotlinx.datetime) // Check for the latest version

            //ktor
            implementation(libs.ktor.client.core) // Core Ktor client
            implementation(libs.ktor.client.serialization) // Serialization support
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json) // JSON support with kotlinx.serialization
            implementation(libs.ktor.client.logging) // Logging dependency

            //MoKo(mobile kotlin)
            implementation(libs.mvvm.core) // only ViewModel, EventsDispatcher, Dispatchers.UI
            implementation(libs.mvvm.compose) // api mvvm-core, getViewModel for Compose Multiplatform


            //kamel
            //implementation(libs.kamel.image.default)
            implementation(libs.kamel)

            implementation(libs.navigator)
            implementation(libs.navigator.screen.model)
            implementation(libs.navigator.transitions)
            implementation(libs.navigator.koin)
            implementation(libs.koin.core)

            implementation(libs.mongodb.realm)
            implementation(libs.kotlin.coroutines)
            implementation(libs.stately.common)


        }
    }
}

android {
    namespace = "com.stevdza_san.todo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.stevdza_san.todo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    androidResources {
        generateLocaleConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

task("testClasses") {}
dependencies {
    implementation(libs.androidx.appcompat)
    //implementation(libs.androidx.constraintlayout)
}
