package com.sumauto.download

import com.sumauto.download.db.AppDataBase
import com.sumauto.download.db.entity.DownloadInfo
import com.sumauto.download.handler.DownloadHandlerFactory


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
class DownloadManager {


    private val handler = DownloadHandlerFactory().singleThreadHandler()

    fun getDownloadInfo(url: String): DownloadInfo? {
        return AppDataBase.get().downloadDao().getDownloadInfo(0)
    }

    fun startDownload(request: DownloadRequest): Long {
        val url = request.url

        val downloadInfo = AppDataBase.get().downloadDao().getDownloadInfo(url)
        if (!request.forceDownload && downloadInfo != null) {
            //查看是否有可用缓存
            return downloadInfo.id
        }

        val task = handler.newTask(request)
        return task.downloadId
    }


    fun observe(id: Long, listener: DownloadListener) {
        AppDataBase.get().downloadDao().observe(id)
    }

    fun isRunning(id: Long):Boolean {
        return handler.isRunning(id)
    }
}