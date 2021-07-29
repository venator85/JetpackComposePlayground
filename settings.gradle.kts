dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
		maven { url = uri("https://venator85-maven.firebaseapp.com/") }
		jcenter()
	}
}
rootProject.name = "ComposePlayground"
include(":app")
