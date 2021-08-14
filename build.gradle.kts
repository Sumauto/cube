// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    extra.apply{
        set("maven_group_id","com.github.sumauto.cube")
    }

    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.1.3")
        classpath ("com.google.protobuf:protobuf-gradle-plugin:0.8.15")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenCentral()

        google()
        maven{
            setUrl("https://jitpack.io")
        }

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
