package com.sumauto.download

import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */
object DownloadClient {
    private val client = OkHttpClient.Builder()
        .followRedirects(true)
        .retryOnConnectionFailure(true)
        .readTimeout(15,TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor {
            DLog.log(it)
        }.setLevel(HttpLoggingInterceptor.Level.NONE))
        .build()

    fun get(url:String):Response{
        val request = Request.Builder().url(url)
            .get()
            .build()
        val newCall = client.newCall(request)
        return newCall.execute()
    }

    fun getRange(url:String,start:Long,end:Long):Response{
        val request = Request.Builder().url(url)
            .get()
            .addHeader("Range","bytes=$start-$end")
            .build()
        val newCall = client.newCall(request)
        return newCall.execute()
    }

}