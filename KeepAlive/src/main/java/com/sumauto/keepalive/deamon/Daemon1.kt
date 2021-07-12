package com.sumauto.keepalive.deamon

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sumauto.helper.log.XLog
import com.sumauto.keepalive.ProcessKeeper

class Daemon1:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        XLog.d(ProcessKeeper.TAG,"Daemon1 onCreate")
    }
}