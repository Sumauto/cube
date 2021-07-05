package com.sumauto

import android.app.Application
import com.sumauto.helper.Sumauto
import com.sumauto.helper.log.XLog
import com.sumauto.utils.LogTag

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Sumauto.init(this)
        XLog.registerTag(LogTag.COROUTINES, XLog.ENABLE)
    }
}