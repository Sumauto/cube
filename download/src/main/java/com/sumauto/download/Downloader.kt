package com.sumauto.download

import com.sumauto.download.exceptions.DownloadException
import com.sumauto.download.exceptions.NetworkException
import com.sumauto.download.exceptions.UnknownException
import com.sumauto.download.source.UrlSource
import com.sumauto.download.space.MainSpace
import java.io.File
import kotlin.Throws


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
object Downloader {

    lateinit var config: DownloadConfig

    private val downloadHandlerMap = mutableMapOf<String, DownloadHandler>()
    internal lateinit var workSpace: MainSpace
    private var hasInitialized = false

    fun init(config: DownloadConfig) {
        if (hasInitialized) {
            return
        }
        this.config = config
        workSpace = MainSpace(File(config.workDir))
        hasInitialized = true
    }

    fun getDownloadInfo(url: String): DownloadInfo {
        val info = DownloadInfo(url)
        val source = UrlSource(url)
        info.downloaded = source.calculateDownloadedBytes()
        info.total = source.totalLength()
        if (source.hasComplete()) {
            info.outputFile = source.downloadSpace.outputFile()
        }
        return info
    }


    @Throws(exceptionClasses = [NetworkException::class, UnknownException::class, DownloadException::class])
    fun create(url: String, options: Options?): DownloadHandler {
        synchronized(this) {

            DLog.log("start")
            var handler = downloadHandlerMap[url]
            if (handler != null) {
                DLog.log("download already started")
                return handler
            }


            val downloadDir = options?.downloadDir
            if (downloadDir != null) {
                workSpace.registerDownloadDir(url, downloadDir,options.dirConflictStrategy)
            }

            handler = DownloadHandler(UrlSource(url))
            downloadHandlerMap[url] = handler

            handler.addDownloadListener(object : DownloadHandler.DownloadListener {
                override fun onSuccess(file: File) {
                    recycle()
                }

                override fun onError(e: Exception) {
                    recycle()
                }

                override fun onCancel() {
                    recycle()
                }

                private fun recycle() {
                    handler.clear()
                    downloadHandlerMap.remove(url)
                }
            })


            return handler
        }
    }


    data class Options(
        val PAGE_SIZE: Int = 0,
        val downloadDir: File?=null,
        val dirConflictStrategy:DirConflictStrategy=DirConflictStrategy.CREATE
    )


}