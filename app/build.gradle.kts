plugins {
	id("com.android.application")
	id("kotlin-android")
}

android {
	compileSdk = 30
	buildToolsVersion = "30.0.3"

	defaultConfig {
		applicationId = "com.example.composeplayground"
		minSdk = 24
		targetSdk = 30
		versionCode = 1
		versionName = "1.0"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
		kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
	}
}

dependencies {
	implementation("androidx.core:core-ktx:1.5.0")
	implementation("androidx.appcompat:appcompat:1.3.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
	implementation("com.google.android.material:material:1.3.0")

	implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
	implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
	implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
	implementation("androidx.activity:activity-compose:1.3.0-beta01")
	implementation("androidx.navigation:navigation-compose:2.4.0-alpha02")

}