plugins {
    id("com.android.library")
    id("maven-publish")
}

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


    sourceSets {
        val dirs = arrayOf("p_scaleimageview", "p_progressview")
        getByName("main") {
            dirs.forEach { dir ->
                java.srcDir("src/${dir}/main/java")
                res.srcDir("src/${dir}/main/res")
            }
        }
    }

    lintOptions {
        isAbortOnError=false
    }
}

dependencies {
    implementation(Dep.viewpager)
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

