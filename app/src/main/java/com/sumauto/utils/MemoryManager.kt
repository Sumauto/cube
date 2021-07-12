package com.sumauto.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Looper
import android.os.Process
import com.sumauto.cube.databinding.ActivityMainBinding
import com.sumauto.helper.Sumauto
import com.sumauto.helper.log.XLog

object MemoryManager {

    const val TAG = "Memory"


    fun memoryTest() {
        Thread {
            runUntilOOM()
        }.start()
    }

    private fun runUntilOOM() {
        val veryLargeMap = mutableListOf<Array<Int?>>()
        while (true) {
            dump()
            for (i in 0..200) {
                veryLargeMap.add(arrayOfNulls<Int?>(10 * 1000))
            }
        }

    }

    fun dump() {
        val am = Sumauto.context().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)

        val appMemory = bit2MB(Runtime.getRuntime().totalMemory())
        val largeHeap = am.largeMemoryClass
        val normalHeap = am.memoryClass

        val sysAvailable = bit2MB(mi.availMem)
        val sysTotal = bit2MB(mi.totalMem)

        val gcTime = bit2MB(mi.threshold)

        val maxMemory = bit2MB(Runtime.getRuntime().maxMemory())
        if (maxMemory - appMemory < gcTime) {
            XLog.d("gc will start")
        }

        XLog.d(
            TAG,
            "${appMemory}MB/(${normalHeap}MB:${largeHeap}MB) system:IDLE=${sysAvailable}MB TOTAL=${sysTotal}MB threshold:${gcTime}MB"
        )
    }

    private fun bit2MB(d: Long): Long {
        return d / 1024 / 1024
    }
}