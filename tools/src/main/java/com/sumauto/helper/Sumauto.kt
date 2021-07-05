package com.sumauto.helper

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalStateException
import java.lang.ref.WeakReference

object Sumauto {
    private var context: WeakReference<Application>? = null

    fun init(app: Application) {
        context = WeakReference(app)
    }

    @JvmStatic
    fun context(): Application {
        if (context == null) {
            throw IllegalStateException("init Sumauto first")
        }
        return context!!.get() ?: throw IllegalStateException("init Sumauto first")
    }

    fun sharedPreference(): SharedPreferences {
        return context().getSharedPreferences("sumauto", Context.MODE_PRIVATE)
    }
}