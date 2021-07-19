package com.sumauto.im

import android.content.Context
import com.sumauto.im.client.WebSocketManagerClient
object IM {
    fun setUp(context: Context){
        WLog.d("IM.setup")
        WebSocketManagerClient.connectService(context)
    }

    fun connect(url:String){
        WLog.d("IM.connect $url")
        WebSocketManagerClient.connect(url)
    }


}