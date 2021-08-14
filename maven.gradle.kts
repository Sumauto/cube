//apply plugin: 'maven-publish'
apply(plugin = "maven-publish")

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "product".
            create<MavenPublication>("maven") {
                from(components["debug"])
                ///Users/lincoln.zhu/.m2/repository/com/sumauto/tools
                groupId = Dep.maven_group_id
            }

        }
    }
}