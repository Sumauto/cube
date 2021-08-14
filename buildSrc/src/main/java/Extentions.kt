/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/24 
 */
import org.gradle.api.artifacts.dsl.DependencyHandler
import java.io.File

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}