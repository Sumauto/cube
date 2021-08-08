package com.sumauto.download

import com.sumauto.download.executor.DownloadWork
import com.sumauto.download.source.AbstractSource
import com.sumauto.download.source.UrlSource
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
object DownloadExecutor {

    val deque = LinkedBlockingDeque<DownloadWork>()
    private var executor = ThreadPoolExecutor(
        3,
        3,
        0,
        TimeUnit.SECONDS,
        deque as LinkedBlockingDeque<Runnable>,
        TF(),
        ThreadPoolExecutor.AbortPolicy()
    )

    fun execute(runnable: DownloadWork) {
        executor.execute(runnable)
    }

    fun cancel(source: AbstractSource) {
        deque.removeIf {
            if (it.source() == source) {
                it.isFinished=true
                return@removeIf true
            }
            false
        }
    }

}

class TF : ThreadFactory {
    private var group: ThreadGroup? = null
    private val threadNumber = AtomicInteger(1)
    private val namePrefix: String

    init {
        val s = System.getSecurityManager()
        group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
        namePrefix = "download-thread-"
    }

    override fun newThread(r: Runnable?): Thread {
        val t = Thread(
            group, r,
            namePrefix + threadNumber.getAndIncrement(),
            0
        )
        if (t.isDaemon) t.isDaemon = false
        if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
        return t
    }

}