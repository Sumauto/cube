package com.sumauto.download.handler


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
class DownloadHandlerFactory {

    fun singleThreadHandler():IHandler{
        return SingleThreadHandler()
    }

}