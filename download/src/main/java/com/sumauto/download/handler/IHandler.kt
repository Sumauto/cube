package com.sumauto.download.handler

import com.sumauto.download.DownloadRequest


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
interface IHandler {

    fun newTask(request: DownloadRequest): RunningTask

    fun isRunning(id: Long):Boolean

}