package com.sumauto

import android.app.Application
import com.sumauto.helper.log.XLog
import com.sumauto.utils.LogTag

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        XLog.registerTag(LogTag.COROUTINES, XLog.ENABLE)
    }
}