package com.sumauto.download

import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
abstract class AbstractSource(var key: String) {

    fun start(): File {
        val workDirPath = DownloadUtil.getWorkDir(key)
        val workDir: File
        if (workDirPath == null) {
            workDir = DownloadInfo.createWorkDir(key)
        } else {
            workDir = File(workDirPath)
        }
        val file = proceed(workDir)
        return file
    }

    abstract fun proceed(workDir: File): File

    fun notifyUpdate() {}

    open fun calculateDownloadedBytes(): Long {
        return 0
    }


}