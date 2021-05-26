package com.sumauto.cube.acessbility

import android.accessibilityservice.AccessibilityButtonController
import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class CubeAccessibilityService : AccessibilityService() {

    companion object {
        const val TAG = "CubeAccess"
    }


    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "onServiceConnected")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d(TAG, "onAccessibilityEvent $event")
        Log.d(TAG, "onAccessibilityEvent text= ${event?.text}")
        when (event?.eventType) {
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
            }
            AccessibilityEvent.TYPE_ANNOUNCEMENT -> {
                TODO()
            }
            AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT -> {
                TODO()
            }
            AccessibilityEvent.TYPE_GESTURE_DETECTION_END -> {
                TODO()
            }
            AccessibilityEvent.TYPE_GESTURE_DETECTION_START -> {
                TODO()
            }
            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END -> {
                TODO()
            }
            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START -> {
                TODO()
            }
            AccessibilityEvent.TYPE_TOUCH_INTERACTION_END -> {
                TODO()
            }
            AccessibilityEvent.TYPE_TOUCH_INTERACTION_START -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_HOVER_ENTER -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_HOVER_EXIT -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_SELECTED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY -> {
                TODO()
            }
            AccessibilityEvent.TYPE_WINDOWS_CHANGED -> {
                TODO()
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                AccessHelper.setText(this,"hi main!")
            }
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                TODO()
            }
        }
    }


    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")

    }
}