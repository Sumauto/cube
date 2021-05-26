package com.sumauto.cube.usage

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.sumauto.helper.log.XLog
import java.util.*


object UsageHelp {
    const val TAG = "UsageHelp"

    fun query(context: Context) {
        val manager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        } else {
            return
        }

        val beginCal: Calendar = Calendar.getInstance()
        beginCal.set(Calendar.DATE, 1)
        beginCal.set(Calendar.MONTH, 0)
        beginCal.set(Calendar.YEAR, 2012)

        val endCal: Calendar = Calendar.getInstance()
        endCal.set(Calendar.DATE, 1)
        endCal.set(Calendar.MONTH, 0)
        endCal.set(Calendar.YEAR, 2014)

        val queryUsageStats: List<UsageStats> = manager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, beginCal.timeInMillis, endCal.timeInMillis)
        queryUsageStats.forEach {
            XLog.d(TAG, Gson().toJson(it))
        }

    }
}