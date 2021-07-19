package com.sumauto.im

import android.text.TextUtils
import android.util.Log

object WLog {
    const val TAG = "web-socket"
    fun logFormat(format: String?, vararg args: Any?) {
        logFormatWithTag(null, format, *args)
    }

    fun logFormatWithTag(tag: String?, format: String?, vararg args: Any?) {
        val msg = String.format(format!!, *args)
        d(tag, msg)
    }

    fun d(msg: String) {
        d(null, msg)
    }

    fun e(t: Throwable?) {
        Log.e(TAG, "error", t)
    }

    fun d(tag: String?, msg: String) {
        var tag = tag
        if (TextUtils.isEmpty(tag)) {
            tag = "default-tag"
        }
        val logMsg = "[$tag]$msg"
        Log.d(TAG, logMsg)
    }
}