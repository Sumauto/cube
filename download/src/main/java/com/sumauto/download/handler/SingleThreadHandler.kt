package com.sumauto.download.handler

import com.sumauto.download.DownloadRequest
import com.sumauto.download.db.AppDataBase
import com.sumauto.download.db.entity.DownloadInfo
import java.util.concurrent.Executors


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
class SingleThreadHandler : IHandler {


    override fun newTask(request: DownloadRequest): RunningTask {
        var task = RunningTaskPool.findTask(request.url)
        if (task != null) {
            return task
        }

        val id = AppDataBase.get().downloadDao().insert(DownloadInfo(url = request.url))
        task = RunningTask(id, request.url)
        RunningTaskPool.stopAllTask()
        RunningTaskPool.addTask(request.url, task)

        return task
    }

    override fun isRunning(id: Long):Boolean {
        return RunningTaskPool.get(id) != null
    }



}