package com.sumauto.download.space

import com.sumauto.download.space.AbsWorkSpace
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/08 
 */

internal class MainSpace(dir: File) : AbsWorkSpace(dir) {


    internal fun createDownloadDir(downloadKey: String): File {
        var path = getDownloadDir(downloadKey)
        if (path == null) {
            val file = File(baseDir, downloadKey)
            file.mkdir()
            putProp(downloadKey, file.absolutePath)
            saveProperties()
            path = file.absolutePath
        }
        return File(path!!)
    }

    internal fun getDownloadDir(downloadKey: String): String? {
        return getProp(downloadKey)
    }
}