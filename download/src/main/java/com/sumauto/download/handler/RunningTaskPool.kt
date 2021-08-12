package com.sumauto.download.handler

import java.util.concurrent.Executors


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
internal object RunningTaskPool {
    private val map = mutableMapOf<String, RunningTask>()
    private val exeutor = Executors.newSingleThreadExecutor()

    fun findTask(url: String): RunningTask? {
        return map[url]
    }

    fun addTask(url: String, task: RunningTask) {
        map[url] = task

        exeutor.execute {
            try {
                task.fetch()
            } catch (e: Exception) {
            }
            map.remove(url)
        }
    }

    fun stopAllTask() {
        map.values.forEach { it.cancel() }
    }

    fun get(id: Long): RunningTask? {
        return map.values.find { it.downloadId == id }
    }
}