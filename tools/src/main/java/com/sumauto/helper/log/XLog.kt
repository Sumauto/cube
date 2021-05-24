package com.sumauto.helper.log

import android.util.Log

object XLog {
    private const val TAG = "[x]"
    private const val DEFAULT_SUB_TAG = "[default]"
    private val TAG_CONFIGS: MutableMap<String, TagConfig> = HashMap()
    const val RUNTIME = 1
    const val ENABLE = 2
    const val DISABLE = 3

    @JvmStatic
    fun registerTag(tag: String, type: Int) {
        val tagConfig = TagConfig(tag, type)
        TAG_CONFIGS[tag] = tagConfig
    }

    @JvmStatic
    fun d(msg: String) {
        d(DEFAULT_SUB_TAG, msg)
    }

    @JvmStatic
    fun e(msg: String) {
        e(DEFAULT_SUB_TAG, msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        log(tag, msg, Log.DEBUG)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        log(tag, msg, Log.ERROR)
    }

    private fun log(tag: String, msg: String, logLevel: Int) {
        val tagConfig = TAG_CONFIGS[tag]
        if (tagConfig != null) {

            if (tagConfig.type == DISABLE) {
                return
            }
            if (tagConfig.type == RUNTIME) {
                if (!Log.isLoggable(tag, logLevel)) {
                    return
                }
            }
        }

        Log.println(logLevel, TAG, "[$tag] $msg")
    }
}