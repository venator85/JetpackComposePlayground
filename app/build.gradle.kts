plugins {
	id("com.android.application")
	id("kotlin-android")
	id("com.github.ben-manes.versions").version("0.39.0")
}

android {
	compileSdk = 30

	defaultConfig {
		applicationId = "com.example.composeplayground"
		minSdk = 24
		targetSdk = 30
		versionCode = 1
		versionName = "1.0"
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
	val logVersion = "0.6.2"
	implementation("eu.alessiobianchi:log:$logVersion")

	implementation("com.google.android.material:material:1.4.0")
	implementation("androidx.core:core-ktx:1.6.0")
	implementation("androidx.appcompat:appcompat:1.3.1")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
	implementation("androidx.datastore:datastore-preferences:1.0.0-rc02")
	implementation("androidx.datastore:datastore:1.0.0-rc02")

	implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
	implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
	implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
	implementation("androidx.activity:activity-compose:1.3.0")
	implementation("androidx.navigation:navigation-compose:2.4.0-alpha05")

}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
	rejectVersionIf {
		isNonStable(candidate.version) && !isNonStable(currentVersion)
	}
}

fun isNonStable(version: String): Boolean {
	val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
	val regex = "^[0-9,.v-]+(-r)?$".toRegex()
	val isStable = stableKeyword || regex.matches(version)
	return isStable.not()
}
