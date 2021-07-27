package com.sumauto.download

import java.io.File
import java.util.*


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
internal object DownloadUtil {

    fun kb(size: Long): Long {
        return size * 1024
    }

    fun mb(size: Long): Long {
        return kb(size) * 1024
    }

    fun getWorkDir(key: String): String? {
        return DownloadInfo.prop.getProperty(key)
    }

}