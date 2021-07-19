package com.sumauto.im.cmds

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConnectP(
    val url:String
) : Parcelable
