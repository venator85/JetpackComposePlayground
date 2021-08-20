buildscript {
	repositories {
		google()
		mavenCentral()
	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.0.1")
		classpath(kotlin("gradle-plugin", version = "1.5.21"))
	}
}

plugins {
	id("com.github.ben-manes.versions") version "0.39.0"
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}
