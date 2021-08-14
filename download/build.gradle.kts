plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

//apply(from = "../maven.gradle.kts")
version = "1.0.1"

android {
    compileSdkVersion(Ver.compileSdkVersion)

    defaultConfig {
        setMinSdkVersion(Ver.minSdkVersion)
        setTargetSdkVersion(Ver.targetSdkVersion)
        versionCode = 1
        versionName = project.version.toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets {
        getByName("test") {
            java {
                srcDir("src/main/java")
            }
        }

    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Ver.kotlin}")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("com.squareup.okhttp3:okhttp:${Ver.okhttp}")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.0")

    testImplementation("com.squareup.okhttp3:okhttp:${Ver.okhttp}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:logging-interceptor:3.12.0")
}



val sourcesJar by tasks.creating(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.convention("sources")
    archiveClassifier.set("sources")

}



afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["debug"])


                artifacts {
                    archives(sourcesJar)
                }

                //~/.m2/repository/com/sumauto/tools
                groupId = Dep.maven_group_id


            }

        }
    }
}

