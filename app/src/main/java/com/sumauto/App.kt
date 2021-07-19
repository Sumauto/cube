package com.sumauto

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.sumauto.helper.Sumauto
import com.sumauto.helper.log.XLog
import com.sumauto.keepalive.ProcessKeeper
import com.sumauto.utils.LogTag
import com.sumauto.utils.MemoryManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Sumauto.init(this)
        XLog.registerTag(LogTag.COROUTINES, XLog.ENABLE)
        //MemoryManager.memoryTest()

        Toast.makeText(this,ApkChannel.getChannel(applicationInfo.sourceDir),Toast.LENGTH_SHORT).show()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ProcessKeeper.init(this)
    }
}