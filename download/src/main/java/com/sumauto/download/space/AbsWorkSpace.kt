package com.sumauto.download.space

import com.sumauto.download.load
import com.sumauto.download.store
import java.io.File
import java.util.*


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/08 
 */
abstract class AbsWorkSpace(
    baseDir: File,
    private var configFileName: String = "workspace"
) {

    var baseDir = baseDir
        private set

    private val spaceFile: File by lazy {
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
        val file = File(baseDir, configFileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        return@lazy file
    }

    private val spaceProp: Properties by lazy {
        val prop = Properties()
        prop.load(spaceFile)
        return@lazy prop
    }

    fun putProp(key: String, v: String) {
        spaceProp.setProperty(key, v)
    }

    fun putAndSaveProp(key: String, v: String) {
        spaceProp.setProperty(key, v)
        saveProperties()
    }

    fun getProp(key: String): String? {
        return spaceProp.getProperty(key)
    }


    fun propKeys(): Set<String> {
        return spaceProp.stringPropertyNames()
    }

    fun saveProperties() {
        spaceProp.store(spaceFile)
    }
}