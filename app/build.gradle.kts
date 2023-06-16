plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}


android {
    namespace = "com.demo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.demo"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled  = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
            buildConfigField("String", "PING_IP", "\"8.8.8.8\"")
        }

        getByName("release") {
            isMinifyEnabled  = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
            buildConfigField("String", "PING_IP", "\"8.8.8.8\"")
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    //Core Libraries
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Activity and Fragment
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    implementation("androidx.databinding:library:3.2.0-alpha11")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    //Sdp Dependency
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    //Navigation Graph
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //Lottie Files
    implementation("com.airbnb.android:lottie:5.2.0")

    //Flexbox Layout Manager
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    //Gif Loader
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.23")



    //Coil Image Loader
    implementation("io.coil-kt:coil:2.2.1")

    //Swipe Refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha06")
    implementation("androidx.datastore:datastore-core:1.0.0-alpha06")

    //Dexter Permission Check
    implementation ("com.karumi:dexter:6.2.3")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")


    implementation ("com.squareup.picasso:picasso:2.8")
}

kapt {
    correctErrorTypes = true
}