package com.sumauto.keepalive.process

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.sumauto.keepalive.utils.ServiceHelper
import com.sumauto.keepalive.utils.XLog

class ResidentService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        XLog.d("ResidentService onCreate")
        ServiceHelper.bind(this, DService1::class.java)
        ServiceHelper.bind(this, DService2::class.java)
        ServiceHelper.bind(this, DLaunchService::class.java)

        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        XLog.d("ResidentService onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }
}