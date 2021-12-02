package com.sumauto.keepalive.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import java.io.FileInputStream
import java.io.IOException
import java.lang.reflect.Method


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/15 
 */
internal object SystemUtils {

    fun isMainProcess(context: Context): Boolean {
        return context.packageName.equals(getProcessName())
    }

    @SuppressLint("ObsoleteSdkInt", "PrivateApi")
    fun getProcessName(): String? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = Application.getProcessName()
            if (processName != null)
                return processName
        }

        try {
            val methodName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                "currentProcessName"
            } else {
                "currentPackageName"
            }
            val processName = Class.forName("android.app.ActivityThread")
                .getDeclaredMethod(methodName)
                .invoke(null) as? String
            if (processName!=null)
                return processName
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return getCurrentProcessName()
    }

    private fun getCurrentProcessName(): String? {
        var `in`: FileInputStream? = null
        try {
            val fn = "/proc/self/cmdline"
            `in` = FileInputStream(fn)
            val buffer = ByteArray(256)
            var len = 0
            var b: Int
            while (`in`.read().also { b = it } > 0 && len < buffer.size) {
                buffer[len++] = b.toByte()
            }
            if (len > 0) {
                return String(buffer, 0, len, Charsets.UTF_8)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    fun setProcessName(name: String) {

        invoke(Process::class.java.name, "setArgV0", arrayOf(String::class.java), name)
        XLog.updateProcessName()
    }

    private fun invoke(
        className: String,
        methodName: String,
        cl: Array<Class<*>>,
        vararg params: Any
    ) {
        try {
            XLog.d("invoke:$methodName")
            val getDeclaredMethod = Class::class.java.getDeclaredMethod(
                "getDeclaredMethod",
                String::class.java,
                arrayOf<Class<*>>().javaClass
            )
            val forName = Class::class.java.getDeclaredMethod("forName", String::class.java)

            val targetClazz = forName.invoke(null,className)
            val targetMethod = getDeclaredMethod.invoke(targetClazz, methodName, cl) as Method
            targetMethod.invoke(null, *params)
        } catch (e: Exception) {
            XLog.error(e)
        }
    }

}