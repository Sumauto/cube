import org.gradle.api.artifacts.dsl.DependencyHandler

/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/24 
 */
object Dep {

    val kotlin = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Ver.kotlin}",
        "androidx.core:core-ktx:1.6.0",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
    )
    val network = listOf(
        "com.squareup.retrofit2:retrofit:${Ver.retrofit}",
        "com.google.code.gson:gson:2.8.7",
        "com.squareup.retrofit2:converter-gson:${Ver.retrofit}",
        "com.squareup.okhttp3:logging-interceptor:3.12.0",
        "com.squareup.okhttp3:okhttp:4.7.2"
    )

    val lifecycle = listOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Ver.lifecycle}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Ver.lifecycle}",
        "androidx.lifecycle:lifecycle-process:${Ver.lifecycle}",
        "androidx.paging:paging-runtime:${Ver.paging}"
    )

    val room = listOf(
        "androidx.room:room-runtime:${Ver.room}",
        "androidx.room:room-ktx:${Ver.room}"
    )

    val navigation = listOf(
        "androidx.navigation:navigation-fragment-ktx:${Ver.nav}",
        "androidx.navigation:navigation-ui-ktx:${Ver.nav}"
    )
    val ui = listOf(
        "androidx.fragment:fragment-ktx:${Ver.fragment}",
        "androidx.appcompat:appcompat:${Ver.appcompat}",
        "com.google.android.material:material:${Ver.material}",
        "androidx.constraintlayout:constraintlayout:${Ver.constraintLayout}",
        "androidx.cardview:cardview:1.0.0"
    )

    val viewpager= listOf(
        "com.google.android.material:material:${Ver.material}"
    )

    val other = listOf(
        "com.blankj:utilcodex:1.30.0",
        "com.github.sumauto:cube:1.0.2"
    )

    val maven_group_id="com.github.sumauto.cube"



}

