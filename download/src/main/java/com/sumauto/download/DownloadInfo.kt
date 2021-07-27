package com.sumauto.download

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
internal object DownloadInfo {
    lateinit var prop: Properties
    lateinit var workDir: File
    lateinit var propFile: File
    const val FILE_NAME = "work_info"

    fun init() {
        workDir = File(Downloader.config.workDir)
        if (!workDir.exists()) {
            workDir.mkdirs()
        }

        prop = Properties()
        propFile = File(workDir, FILE_NAME)
        if (!propFile.exists()) {
            propFile.createNewFile()
        }
        prop.load(FileInputStream(propFile))
    }


    fun createWorkDir(key: String): File {
        val workDir = DownloadUtil.getWorkDir(key)
        return if (workDir == null) {
            val dirName = UUID.randomUUID().toString()
            val file = File(DownloadInfo.workDir, dirName)
            file.mkdir()
            prop.setProperty(key, file.absolutePath)
            prop.store(FileOutputStream(propFile), "")
            file
        } else {
            File(workDir)
        }
    }

}