package com.sumauto.keepalive.process

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.sumauto.keepalive.utils.XLog

class DService1:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        XLog.d("Assist1 onBind")
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        XLog.d("Assist1 onCreate")
    }
}