package com.sumauto.keepalive.utils

import android.util.Log


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/15 
 */
internal object XLog {
    const val TAG = "ProcessKeeper"

    private var processName: String? = SystemUtils.getProcessName()


    fun updateProcessName() {
        d("updateProcessName start")
        processName = SystemUtils.getProcessName()!!
        d("updateProcessName end")
    }

    @JvmStatic
    fun d(msg: String) {
        Log.d(TAG, "[$processName]$msg")
    }

    @JvmStatic
    fun error(t: Throwable) {
        error("", t)
    }

    @JvmStatic
    fun error(msg: String, t: Throwable) {
        Log.e(TAG, "[$processName] $msg ", t)
    }

    fun logMethod(extra: String) {
        val msg = Thread.currentThread().stackTrace[3].run {
            "$className.$methodName()"
        }
        d(msg + extra)
    }
}