@file:Suppress("unused")

package com.sumauto.helper.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.NetworkInterface
import java.util.*

object UniqueID {

    var mCachedMacId: String? = null

    @SuppressLint("HardwareIds")
    fun getId(context: Context): String? {
        if (!TextUtils.isEmpty(mCachedMacId)) {
            return mCachedMacId
        }
        var id: String?
        val sdkInt = Build.VERSION.SDK_INT
        id = when {
            sdkInt < Build.VERSION_CODES.M -> {
                getMacFromWifiManager(context)
            }
            sdkInt < Build.VERSION_CODES.N -> {
                getMacFromCMD()
            }
            else -> {
                getMacFromHardware()
            }
        }
        if (isBadId(id)) {
            //8.0后,由硬件标识+系统用户+应用签名+应用包名 生成
            id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }
        if (isBadId(id)) {
            //到这一步，只能保证不清空数据、不卸载到情况下有效
            id = UUID.randomUUID().toString()
        }
        mCachedMacId = id
        return id
    }

    private fun isBadId(id: String?): Boolean {
        return (TextUtils.isEmpty(id)
                || "00:00:00:00" == id || "02:00:00:00:00:00" == id)
    }


    private fun getMacFromCMD(): String? {
        var macSerial: String? = null
        var str: String? = ""
        try {
            val pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)
            while (null != str) {
                str = input.readLine()
                if (str != null) {
                    macSerial = str.trim { it <= ' ' }
                    break
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return macSerial
    }

    private fun getMacFromWifiManager(context: Context): String? {
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var info: WifiInfo? = null
        try {
            info = wifi.connectionInfo
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (info == null) {
            return null
        }
        @SuppressLint("HardwareIds") var mac = info.macAddress
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH)
        }
        return mac
    }

    private fun getMacFromHardware(): String {
        try {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }
                if (!TextUtils.isEmpty(res1)) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}