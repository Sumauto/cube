package com.sumauto.download.executor

import com.sumauto.download.source.AbstractSource


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/08 
 */
abstract class DownloadWork : Runnable {
    var isFinished = false

    abstract fun source(): AbstractSource
}