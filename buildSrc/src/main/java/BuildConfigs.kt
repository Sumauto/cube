import org.gradle.internal.Specs.isInstance
import java.lang.reflect.Type

/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/24 
 */
object BuildConfigs {
    const val TAURUS = "www.baidu.com"
    const val A_BOOLEAN = true
    const val A_INT = 3

    private val NONE = FieldItem("", "", "")

    fun get(): List<FieldItem> {
        val primaryList = listOf<Type>(
            Boolean::class.java,
            Float::class.java,
            Double::class.java,
            Int::class.java
        )
        return BuildConfigs::class.java.fields.map {
            val value = it.get(null).toString()
            when {
                primaryList.contains(it.type) -> {
                    return@map FieldItem(it.type.simpleName, it.name, value)
                }
                it.type == String::class.java -> {
                    return@map FieldItem("String", it.name, "\"$value\"")
                }
                else -> {
                    return@map NONE
                }
            }

        }.toList().filter { it != NONE }
    }


    data class FieldItem(
        val type: String,
        val name: String,
        val value: String
    )


}