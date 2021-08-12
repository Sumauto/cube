package com.sumauto.download.space

import com.sumauto.download.db.AppDataBase
import com.sumauto.download.db.entity.DownloadInfo
import com.sumauto.download.space.AbsWorkSpace
import okhttp3.internal.toLongOrDefault
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	下载目录工作区
 * History:		2021/08/08 
 */
const val P_KEY_DONE = "done"

const val P_KEY_FILE = "fileName"
const val P_KEY_SIZE = "fileSize"
const val P_KEY_RANGE = "support_range"

class DownloadSpace(baseDir: File) : AbsWorkSpace(baseDir, "dInfo") {



    fun complete() {
        putProp(P_KEY_DONE, "1")
        saveProperties()
    }


    fun hasComplete(): Boolean {
        if (getProp(P_KEY_DONE) == "1") {
            return true
        }
        return false
    }

    fun outputFile(): File {
        return File(baseDir, fileName())
    }

    fun fileName(): String {
        return getProp(P_KEY_FILE) ?: ""
    }

    fun totalLength(): Long {
        val prop = getProp(P_KEY_SIZE) ?: return 0L
        return prop.toLongOrDefault(0L)
    }

    fun saveDownloadInfo(fileName: String, size: Long, rangeSupport: Boolean) {
        putProp(P_KEY_FILE, fileName)
        putProp(P_KEY_SIZE, size.toString())
        if (rangeSupport) {
            putProp(P_KEY_RANGE, "1")
        } else {
            putProp(P_KEY_RANGE, "0")
        }
        saveProperties()
    }


    fun supportRange(): Boolean {
        return "1" == getProp(P_KEY_RANGE)
    }

}