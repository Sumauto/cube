package com.sumauto.helper.store

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object FileManager {

    fun readText(path: String): String {
        return File(path).readText()
    }

    fun readText(inputStream: InputStream): String {
        return inputStream.bufferedReader().use(BufferedReader::readText)
    }

    fun copy(srcFile: File,target:File) {
        srcFile.copyTo(target)
    }
}