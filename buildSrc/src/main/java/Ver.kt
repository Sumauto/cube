/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/23 
 */
object Ver {
    const val compileSdkVersion = 30
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val kotlin = "1.5.21"
    const val appcompat = "1.3.1"
    const val material = "1.4.0"
    const val constraintLayout = "2.0.4"
    const val okhttp = "4.7.2"
    const val retrofit = "2.9.0"
    const val nav = "2.3.5"
    const val room = "2.3.0"
    const val fragment = "1.3.5"

    const val lifecycle = "2.4.0-alpha02"

    const val paging = "3.0.0"

    const val version_name = "1.0.0"

    fun versionCode(): Int {
        val split = version_name.split(".")
        val major = split[0].toInt() * 10000
        val minor = split[1].toInt() * 100
        val build = split[2].toInt()
        return major + minor + build
    }
}