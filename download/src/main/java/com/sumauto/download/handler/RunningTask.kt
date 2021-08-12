package com.sumauto.download.handler

import com.sumauto.download.DownloadHandler
import com.sumauto.download.DownloadListener
import com.sumauto.download.db.AppDataBase
import com.sumauto.download.db.entity.DownloadInfo
import com.sumauto.download.source.AbstractSource
import com.sumauto.download.source.UrlSource
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
class RunningTask(var downloadId: Long, var url: String) {

    private var downloadHandler = DownloadHandler(UrlSource(url))

    init {

        downloadHandler.addProgressListener(object : DownloadHandler.DownloadProgressListener {

            override fun onProgressUpdate(downloaded: Long, total: Long) {
                AppDataBase.get().downloadDao().updateProgress(downloadId,downloaded, total)
            }
        })
    }

    fun fetch(): Result {
        //没有正在运行的任务
        return downloadHandler.fetch()
    }

    fun cancel() {
        downloadHandler.cancel()
    }

}