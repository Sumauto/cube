package com.sumauto.keepalive

import android.content.Context
import com.sumauto.helper.utils.SystemUtils
import com.sumauto.keepalive.internal.VMRuntimeHelper

object ProcessKeeper {
    const val TAG = "ProcessKeeper"

    //abc.er:sumauto023
    //abc.er:sumauto1
    fun init(context: Context) {
        val processName = SystemUtils.getProcessName()
        processName?.apply {
            if (matches(Regex("${context.packageName}:sumauto\\d{1,}"))) {
                VMRuntimeHelper.init1()
            }
        }
        if (SystemUtils.isMainProcess(context)) {

        }
    }
}