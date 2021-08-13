buildscript {
	val compose_version by extra("1.0.1")
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.1.0-alpha08")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}