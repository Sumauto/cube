package com.sumauto.utils

import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.core.view.accessibility.AccessibilityManagerCompat
import com.sumauto.helper.log.XLog
import kotlinx.coroutines.*


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/06 
 */
object AccessibilityChecker {

    const val TAG = "AccessibilityChecker"

    fun check(context: Context) {
        val manager: AccessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        AccessibilityManagerCompat.addTouchExplorationStateChangeListener(manager) {
            XLog.d(TAG, "addTouchExplorationStateChangeListener $it")
        }

        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch {
            while (true) {
                XLog.d(TAG, "service Count:${manager.installedAccessibilityServiceList.size}")

                val allService =
                    manager.installedAccessibilityServiceList.filter { it.packageNames == null }
                val onlyService=manager.installedAccessibilityServiceList.filter { it.packageNames!=null&&it.packageNames.contains(context.packageName) }

                allService.forEach {
                    XLog.d(TAG,"ALL:${it.id}")
                }

                onlyService.forEach {
                    XLog.d(TAG,"Only:${it.id}")
                }
                delay(5000)
            }
        }
    }
}