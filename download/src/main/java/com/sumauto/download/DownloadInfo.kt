package com.sumauto.download

import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/09 
 */
data class DownloadInfo(
    val url: String,
    var downloaded: Long = 0,
    var total: Long = 0,
    var outputFile: File?=null
)
