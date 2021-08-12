package com.sumauto.download

import com.sumauto.download.db.entity.DownloadInfo


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
interface IDownloader {
    /**
     * 查询下载信息
     */
    fun getDownloadInfo(url: String): DownloadInfo?

    fun startDownload(request:DownloadRequest)

    fun observe(url:String)

    fun isRunning(url:String)
}