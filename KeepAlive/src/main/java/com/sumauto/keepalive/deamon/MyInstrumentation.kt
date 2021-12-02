package com.sumauto.keepalive.deamon

import android.app.Instrumentation
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Trace
import androidx.core.os.TraceCompat
import com.sumauto.keepalive.process.DService1
import com.sumauto.keepalive.utils.XLog

class MyInstrumentation:Instrumentation() {
    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        XLog.d("MyInstrumentation.onCreate $arguments")
        targetContext.startService(Intent(targetContext, DService1::class.java))
    }
}