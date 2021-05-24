package com.sumauto.helper.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat

object DeviceUtils {

    fun isMainProcess(context: Context): Boolean {
        return context.packageName.equals(getProcessName())
    }

    @SuppressLint("ObsoleteSdkInt", "PrivateApi")
    private fun getProcessName(): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName()
        }

        try {
            val methodName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                "currentProcessName"
            } else {
                "currentPackageName"
            }
            return Class.forName("android.app.ActivityThread")
                    .getDeclaredMethod(methodName)
                    .invoke(null) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    @SuppressLint("All")
    fun getIMEI(context: Context): String? {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            try {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                return tm.imei
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
}