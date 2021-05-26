package com.sumauto.cube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dalvik.system.DexClassLoader

class DexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dex)
        val dir = getDir("jar", MODE_PRIVATE)
        val dexClassLoader: DexClassLoader = DexClassLoader("test.dex", dir.absolutePath, null, classLoader)
        try {
            var cls = dexClassLoader.loadClass("")
        } catch (e: Exception) {
        }
    }
}