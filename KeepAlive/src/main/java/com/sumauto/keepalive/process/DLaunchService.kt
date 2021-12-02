package com.sumauto.keepalive.process

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.sumauto.keepalive.utils.ServiceHelper
import com.sumauto.keepalive.utils.XLog

class DLaunchService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        XLog.d("DaemonService onCreate")
        ServiceHelper.bind(this, ResidentService::class.java)
        ServiceHelper.bind(this, DService1::class.java)
        ServiceHelper.bind(this, DService2::class.java)
//        val intent = packageManager.getLaunchIntentForPackage(packageName)
//        startActivity(intent)
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            0,
//            PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        )
    }
}