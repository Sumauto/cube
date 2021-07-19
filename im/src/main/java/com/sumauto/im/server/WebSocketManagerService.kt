package com.sumauto.im.server

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import com.sumauto.im.WLog
import com.sumauto.im.client.WebSocketManagerClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

internal class WebSocketManagerService : Service() {
    companion object {
        const val TAG = "Server"
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            WLog.d(TAG, "bind client connected")

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            WLog.d(TAG, "bind client connected")
        }

    }

    override fun onCreate() {
        super.onCreate()
        WLog.d(TAG,"onCreate ${Process.myPid()}-${Binder.getCallingUid()}-${Process.myUid()}")

        bindService(Intent(this, WebSocketManagerClient::class.java), connection, BIND_AUTO_CREATE)
    }

    override fun onBind(intent: Intent?): IBinder {
        WLog.d(TAG,"onBind")
        return object : Binder() {
            override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
                WLog.d(TAG,"onTransact $code")
                return super.onTransact(code, data, reply, flags)
            }
        }
    }

    fun connect(url: String): WebSocket {
        val request = Request.Builder().url(url).build()

        return OkHttpClient().newBuilder().build()
            .newWebSocket(request, object : WebSocketListener() {

            })
    }

}