package com.sumauto.im.server

import android.os.Binder
import android.os.Parcel
import com.sumauto.im.cmds.ConnectP
import com.sumauto.im.cmds.TransactCode

class ServerBinder : Binder() {
    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when(code){
            TransactCode.CONNECT->{
            }
            else ->{
                return false
            }
        }
        return true
    }
}