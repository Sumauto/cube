package com.sumauto.im.client

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.Process
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sumauto.im.WLog
import com.sumauto.im.cmds.TransactCode
import com.sumauto.im.server.WebSocketManagerService
import okhttp3.*
import java.io.File

internal class WebSocketManagerClient : Service() {


    companion object {
        const val TAG = "Client"
        var managerData = MutableLiveData<IBinder?>()

        internal fun connectService(context: Context) {
            context.startService(Intent(context, WebSocketManagerClient::class.java))
        }

        private fun runBinderOnWay(
            code: Int,
            transact: (data: Parcel) -> Any
        ) {
            runBinder(code, transact, null)
        }


        private fun runBinder(
            code: Int,
            transact: (data: Parcel) -> Any,
            onReply: ((reply: Parcel) -> Any)?
        ) {

            managerData.observeForever(object : Observer<IBinder?> {
                override fun onChanged(service: IBinder?) {
                    if (service != null) {
                        managerData.removeObserver(this)

                        val reply = Parcel.obtain()
                        val data = Parcel.obtain()
                        try {
                            WLog.d(TAG, "start transact $code")
                            transact(data)
                            service.transact(code, data, reply, 0)

                            onReply?.invoke(reply)
                            WLog.d(TAG, "transact success $code")

                        } catch (e: Exception) {
                            WLog.e(e)
                            e.printStackTrace()
                        } finally {
                            data.recycle()
                            reply.recycle()
                        }
                    }
                }

            })

        }

        fun send(text: String) {
            runBinderOnWay(TransactCode.SEND_TEXT) {
                it.writeString(text)
            }
        }

        fun connect(url: String) {

            runBinder(TransactCode.CONNECT, {
                it.writeString(url)
            }, {

            })

        }
    }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            WLog.d(TAG, "bind server connected")
            managerData.value = service
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            WLog.d(TAG, "bind server disconnect")
            managerData.value = null

        }

    }

    override fun onCreate() {
        super.onCreate()
        WLog.d(TAG, "onCreate ${Process.myPid()}-${Binder.getCallingUid()}-${Process.myUid()}")
        bindService(
            Intent(this, WebSocketManagerService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        if (managerData.value != null) {
            unbindService(connection)
            managerData.value = null
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        WLog.d(TAG, "onBind")
        return Binder()
    }


    fun connect(url: String): WebSocket {
        val request = Request.Builder().url(url)
            .cacheControl(CacheControl.FORCE_CACHE)
            .build()

        return OkHttpClient().newBuilder()
            .addNetworkInterceptor { chain ->
                chain.proceed(chain.request())
            }
            .authenticator(Authenticator.NONE)
            .cache(Cache(File(""), 20))
            .retryOnConnectionFailure(true)
            .build()
            .newWebSocket(request, object : WebSocketListener() {

            })
    }

}