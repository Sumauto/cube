package com.sumauto.download

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.*


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/27 
 */


val PAGE_SIZE = DownloadUtil.mb(10)

val work_dir = File("work_dir")


class UrlSource(var url: String) : AbstractSource(URLEncoder.encode(url, "utf-8")) {

    private var supportRange = false
    private var fileName: String? = ""

    override fun proceed(workDir: File): File {
        //检查是否已存在下载任务
        val propFile = File(workDir, "download_info")
        if (!propFile.exists()) {
            propFile.createNewFile()
        }
        val properties = Properties()
        properties.load(FileInputStream(propFile))

        //判断是否支持切片
        val slice = getSlice(properties, propFile)
        if (slice != null) {
            if (!slice.isEmpty()) {
                slice.forEach {
                    download(workDir, it)
                }
            }
        } else {

        }
        mergeSlices(workDir)
        return File("")
    }

    private fun mergeSlices(workDir: File) {

    }

    private fun updateSliceInfo(sliceId: String) {

    }


    /**
     * 返回null不支持切片
     */
    private fun getSlice(properties: Properties, propFile: File): List<Slice>? {
        val keys: Set<String> = properties.stringPropertyNames()
        DLog.log("getSlice keys.size=${keys.size}")
        if (keys.isEmpty()) {

            val response = DownloadClient.getRange(url, 0, 10)
            var len = 0L
            if (response.code == HttpURLConnection.HTTP_PARTIAL) {
                len = response.header("Content-Range", "0-10/0")!!.split("/")[1].toLong()
                fileName = response.header("Content-Disposition", null)?.let {
                    it.substring(it.indexOf("filename=") + 9)
                }
                DLog.log("fileName=$fileName")
                properties["fileName"] = fileName
                supportRange = true
            }
            response.close()

            if (!supportRange) {
                return null
            }

            val sliceCount = len / PAGE_SIZE
            var start = 0L
            DLog.log("len=$len count=$sliceCount")
            for (i in 0 until sliceCount) {
                val sliceSize = if (i == sliceCount - 1) {
                    PAGE_SIZE + len % PAGE_SIZE
                } else {
                    PAGE_SIZE
                }
                properties["slice_$i"] = "$start/0/$sliceSize"
                start += sliceSize
            }
            properties.store(FileOutputStream(propFile), "")
        }
        val slices = mutableListOf<Slice>()

        properties.stringPropertyNames().filter { it.startsWith("slice_") }.forEach { key ->
            val pValue = properties.getProperty(key)
            val property = pValue.split("/").map { p_item ->
                p_item.toLong()
            }
            DLog.log("add slices $pValue ")
            if (property[1] < property[2]) {
                slices += Slice(key, property[0], property[1], property[2])
            } else if (property[1] > property[2]) {
                throw RuntimeException("wrong!!$key=$pValue")
            }
        }

        return slices

    }

    private fun checkIfDownloadExists(): Boolean {
        return false
    }

    private fun createSliceInfo(slices: List<Slice>) {

    }

    private fun rangeStream(start: Long, end: Long): InputStream {
        return FileInputStream(File(""))
    }


    private fun download(dir: File, slice: Slice) {
        val sliceFile = File(dir, slice.key)
        val randomAccessFile = RandomAccessFile(sliceFile, "rw")
        if (randomAccessFile.length() == 0L) {
            randomAccessFile.setLength(slice.len)
        }

        randomAccessFile.seek(slice.downloaded_size)
        val startOffset = slice.start + slice.downloaded_size
        val endPosition = slice.start + slice.len
        val rangeResponse = DownloadClient.getRange(url, startOffset, endPosition)
        if (rangeResponse.code != HttpURLConnection.HTTP_PARTIAL) {
            throw RuntimeException("Range not support")
        }

        val byteStream = rangeResponse.body!!.byteStream()
        var readLen = 0
        val buffer = ByteArray(DownloadUtil.kb(10).toInt())
        while (byteStream.read(buffer).also { readLen = it } != -1) {
            randomAccessFile.write(buffer, 0, readLen)
        }
    }

    data class Slice(
        val key: String,
        val start: Long,
        val downloaded_size: Long,
        val len: Long
    )
}