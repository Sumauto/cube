//apply plugin: 'maven-publish'
apply(plugin = "maven-publish")


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