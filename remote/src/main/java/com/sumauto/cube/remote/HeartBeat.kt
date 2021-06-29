package com.sumauto.cube.remote

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import java.util.*

object HeartBeat {

    private var handler: Handler? = null


    fun start() {

        stop()

        val handlerThread = object : HandlerThread(RService.TAG) {
            override fun onLooperPrepared() {
                super.onLooperPrepared()
                handler = Handler(looper) {
                    Log.d(RService.TAG, "heartBeat:${it.obj}")
                    false
                }
            }
        }
        handlerThread.start()

        while (handler == null) {
            Log.d(RService.TAG, "try start ")
            Thread.sleep(100)
        }
        Log.d(RService.TAG, "start success")
        Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    showLog("running...")
                }
            }, 1000, 1000)
        }
    }

    fun showLog(msg: String) {
        handler?.sendMessage(Message.obtain().apply {
            obj = msg
        })
    }

    fun stop() {
        handler?.looper?.quit()
        handler = null
    }
}