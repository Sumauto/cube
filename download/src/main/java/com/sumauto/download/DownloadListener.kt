package com.sumauto.download

import com.sumauto.download.source.AbstractSource
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
open class DownloadListener {

    open  fun onSuccess(source: AbstractSource, file: File) {}

    open fun onError(source: AbstractSource, e: Exception) {}

    open fun onCancel(source: AbstractSource) {}

    open fun onProgressUpdate(source: AbstractSource, downloaded: Long, total: Long) {}
}