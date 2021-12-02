package com.sumauto.keepalive.process

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.sumauto.keepalive.utils.XLog

class DService2:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        XLog.d("Assist2 onBind")
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        XLog.d("Assist2 onCreate")
    }
}