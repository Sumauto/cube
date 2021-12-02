package com.sumauto.keepalive.deamon

import com.sumauto.keepalive.utils.XLog
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel

object FileLocker {
    private var fileChannel: FileChannel? = null

    fun startLock(file: File, info: StartInfo) {

        val randomAccessFile = RandomAccessFile(file, "rw")
        val ch = randomAccessFile.channel
        XLog.d("lock ${file.name}")
        ch.lock()
        XLog.d("lock success")
        randomAccessFile.setLength(0)
        randomAccessFile.writeBytes(info.toString())
        fileChannel = ch
    }

    fun startLockOnGuard(file: File): StartInfo {
        val randomAccessFile = RandomAccessFile(file, "rw")
        val ch = randomAccessFile.channel
        XLog.d("lock $file")
        ch.lock()
        val string = randomAccessFile.readLine()
        val si = StartInfo.create(string)
        XLog.d(si.dump())
        XLog.d("lock success,means ${si.processName} has died")
        return si
    }
}