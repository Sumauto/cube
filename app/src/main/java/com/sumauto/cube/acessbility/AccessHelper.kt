package com.sumauto.cube.acessbility

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.accessibility.AccessibilityNodeInfo

object AccessHelper {
    fun setText(service: AccessibilityService,text:String) {
        val findFocus = service.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
        findFocus?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, Bundle().apply {
            putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
        })
    }

}