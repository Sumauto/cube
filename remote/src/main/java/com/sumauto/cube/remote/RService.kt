package com.sumauto.cube.remote

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RService : Service() {

    companion object {
        const val TAG = "_RService"
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return RBinder()
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        HeartBeat.start()
        startForeground(1, Notification())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        HeartBeat.stop()
    }
}