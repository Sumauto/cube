package com.sumauto.download.source

import com.sumauto.download.DownloadExecutor
import com.sumauto.download.DownloadHandler
import com.sumauto.download.Downloader
import com.sumauto.download.md5
import com.sumauto.download.space.DownloadSpace
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */

abstract class AbstractSource(var uri: String) {

    val downloadSpace: DownloadSpace by lazy {
        DownloadSpace(Downloader.workSpace.createDownloadDir(uri))
    }

    var listener: DownloadHandler.DownloadProgressListener? = null

    var isCancelRequested = false
        private set

    fun cancel() {
        isCancelRequested = true
        DownloadExecutor.cancel(this)
    }

    fun execute(): File {

        if (downloadSpace.hasComplete()) {
            //已完成
            return downloadSpace.outputFile()
        }

        val file = proceed()
        downloadSpace.complete()

        return file
    }

    abstract fun proceed(): File

    open fun calculateDownloadedBytes(): Long {
        return 0
    }

    fun publishProgress(downloaded: Long) {
        listener?.onProgressUpdate(downloaded, totalLength())
    }

    fun totalLength(): Long {
        return downloadSpace.totalLength()
    }

    fun hasComplete(): Boolean {
        return downloadSpace.hasComplete()
    }

    fun saveDownloadInfo(fileName: String, size: Long, rangeSupport: Boolean) {
        downloadSpace.saveDownloadInfo(fileName, size, rangeSupport)
    }


    fun supportRange(): Boolean {
        return downloadSpace.supportRange()
    }

}