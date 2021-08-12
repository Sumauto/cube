package com.sumauto.download

import com.sumauto.download.exceptions.CancelException
import com.sumauto.download.exceptions.UnknownException
import com.sumauto.download.source.AbstractSource
import com.sumauto.download.handler.Result
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/29 
 */
class DownloadHandler(var source: AbstractSource) {

    init {
        source.listener = object : DownloadProgressListener {
            override fun onProgressUpdate(downloaded: Long, total: Long) {
                notifyProgress(downloaded, total)
            }
        }
    }

    private fun notifyProgress(downloaded: Long, total: Long) {
        downloadProgressListeners.forEach {
            it.onProgressUpdate(downloaded, total)
        }
    }

    private val downloadListeners = mutableSetOf<DownloadListener>()
    private val downloadProgressListeners = mutableSetOf<DownloadProgressListener>()
    private val result = Result()
    private var status = Status.INIT

    private fun notifySuccess(file: File) {
        synchronized(this) {
            status = Status.DONE
            result.successFile = file
            notifyAll()
            downloadListeners.toList().forEach {
                it.onSuccess(source,file)
            }
        }
    }


    private fun notifyCanceled(e: CancelException) {
        synchronized(this) {
            status = Status.DONE
            result.exception = e
            notifyAll()
            downloadListeners.toList().forEach {
                it.onCancel(source)
            }
        }
    }

    private fun notifyFail(e: Exception) {
        synchronized(this) {
            status = Status.DONE
            result.exception = e
            notifyAll()
            downloadListeners.toList().forEach {
                it.onError(source,e)
            }
        }


    }

    fun addDownloadListener(listener: DownloadListener?) {
        if (listener != null) {
            downloadListeners.add(listener)
        }
    }

    fun removeDownloadListener(listener: DownloadListener) {
        downloadListeners.remove(listener)
    }

    fun addProgressListener(listener: DownloadProgressListener?) {
        if (listener != null) {
            downloadProgressListeners.add(listener)
        }
    }

    fun removeProgressListener(listener: DownloadProgressListener) {
        downloadProgressListeners.remove(listener)
    }

    private fun start() {
        synchronized(this) {
            if (status != Status.INIT) {
                throw UnknownException("already started!")
            }
            status = Status.RUNNING
        }

        try {
            val file = source.execute()
            notifySuccess(file)
        } catch (e: CancelException) {
            notifyCanceled(e)
        } catch (e: Exception) {
            e.printStackTrace()
            notifyFail(e)
        }
    }

    /**
     * 1.如果文件已经下载直接返回下载结果
     * 2.如果任务还没启动，启动任务，并等待结果
     * 3.如果正在执行等待结果
     */
    fun fetch(): Result {
        synchronized(this) {
            if (status == Status.INIT) {
                start()
            } else if (status == Status.DONE) {
                return result
            }

        }
        return getResultSync()
    }

    fun cancel() {
        source.cancel()
    }

    fun clear() {
        synchronized(this) {
            downloadListeners.clear()
            downloadProgressListeners.clear()
        }
    }

    private fun getResultSync(): com.sumauto.download.handler.Result {
        synchronized(this) {
            while (status != Status.DONE) {
                try {
                    this.wait()
                } catch (e: Exception) {
                }
            }
        }
        return result
    }

    fun sendCurrentProgress() {
        val totalLength = source.totalLength()
        val downloaded = source.calculateDownloadedBytes()
        notifyProgress(downloaded, totalLength)
    }

    interface DownloadProgressListener {
        fun onProgressUpdate(downloaded: Long, total: Long) {}
    }

    enum class Status {
        INIT, RUNNING, DONE
    }
}