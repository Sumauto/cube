package com.sumauto.download

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/28 
 */

fun progress(downloaded: Long, total: Long): Float {
    /**
     * @param downloaded 已下载
     * @param total 总长度
     * @return 0.0-100.0的float
     */
    return ((downloaded/total.toDouble())*100).toFloat()
}

fun Properties.store(file: File, comments: String = "") {
    store(FileOutputStream(file), comments)
}

fun Properties.load(file: File) {
    load(FileInputStream(file))
}

fun kb(size: Long): Long {
    return size * 1024
}

fun mb(size: Long): Long {
    return kb(size) * 1024
}

fun md5(text: String): String {
    try {
        //获取md5加密对象
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        //对字符串加密，返回字节数组
        val digest: ByteArray = instance.digest(text.toByteArray())
        val sb = StringBuffer()
        for (b in digest) {
            //获取低八位有效值
            val i: Int = b.toInt() and 0xff
            //将整数转化为16进制
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                //如果是一位的话，补0
                hexString = "0" + hexString
            }
            sb.append(hexString)
        }
        return sb.toString()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return URLEncoder.encode(text, "utf-8")
}