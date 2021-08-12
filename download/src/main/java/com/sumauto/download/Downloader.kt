package com.sumauto.download

import com.sumauto.download.db.AppDataBase
import com.sumauto.download.db.entity.DownloadInfo
import com.sumauto.download.exceptions.DownloadException
import com.sumauto.download.exceptions.NetworkException
import com.sumauto.download.exceptions.UnknownException
import com.sumauto.download.source.AbstractSource
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


    fun getDownloadInfo(url: String): DownloadInfo? {

        return AppDataBase.get().downloadDao().getDownloadInfo(url)
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

            val source = UrlSource(url)
            handler = DownloadHandler(source)
            downloadHandlerMap[url] = handler

            handler.addDownloadListener(object : DownloadListener() {

                override fun onSuccess(source: AbstractSource, file: File) {
                    super.onSuccess(source, file)
                    recycle()
                }

                override fun onCancel(source: AbstractSource) {
                    super.onCancel(source)
                    recycle()
                }

                override fun onError(source: AbstractSource, e: Exception) {
                    super.onError(source, e)
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
        val PAGE_SIZE: Int = 0
    )


}