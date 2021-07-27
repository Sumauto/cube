package com.sumauto.download

import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
object Downloader {
    lateinit var config: DownloadConfig

    fun init(config: DownloadConfig) {
        this.config = config


        DownloadInfo.init()
    }

    fun start(url:String) {
        println("start")
        val source = UrlSource(url)
        source.start()
    }
}