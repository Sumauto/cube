package com.sumauto.download.source

import com.sumauto.download.*
import com.sumauto.download.exceptions.CancelException
import com.sumauto.download.exceptions.NetworkException
import com.sumauto.download.exceptions.UnknownException
import com.sumauto.download.executor.DownloadWork
import okhttp3.Response
import okhttp3.internal.closeQuietly
import okhttp3.internal.toLongOrDefault
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.lang.Thread.sleep
import java.net.HttpURLConnection


/*
 * Copyright:
 * Author: 		Lincoln
 * Description:
 * History:		2021/07/27
 */


val PAGE_SIZE = mb(10)

private const val P_KEY_SLICE_PREFIX = "slice_"

class UrlSource(var url: String) : AbstractSource(url) {

    override fun proceed(): File {

        //判断是否支持切片
        detectDownloadInfo()

        if (!supportRange()) {
            DLog.log("Range not support")
            //不支持range直接下载
            return download()
        }

        //获取还未下载完成的切片
        val notFinishedSlices = getNotFinishedSlices()

        if (notFinishedSlices.isNotEmpty()) {
            val workers = notFinishedSlices.map { SliceWorker(downloadSpace.baseDir, it) }

            workers.forEach { work ->
                DownloadExecutor.execute(work)
            }

            val total = totalLength()
            var leftSize: Long

            while (true) {
                var hasWorkRunning = false
                leftSize = 0L
                for (work in workers) {
                    if (!work.isFinished) {
                        hasWorkRunning = true
                    }
                    val ws = work.slice
                    putSlice(ws.key, ws.start, ws.downloaded_size, ws.len)
                    leftSize += ws.len - ws.downloaded_size
                    if (ws.downloaded_size > ws.len) {
                        throw UnknownException("download slice error downloaded>len $ws")
                    }
                }
                DLog.log("left:$leftSize total:${total}")
                publishProgress(total - leftSize)
                downloadSpace.saveProperties()
                //每400ms更新下载进度
                if (hasWorkRunning) {
                    sleep(400)
                } else {
                    break
                }
            }


            if (notFinishedSlices.any { it.downloaded_size < it.len }) {
                if (isCancelRequested) {
                    DLog.log(
                        "canceled $leftSize/${total} progress:${
                            progress(
                                total - leftSize,
                                total
                            )
                        }%"
                    )
                    throw CancelException()
                }
                //存在未完成的切片
                throw NetworkException("not all slice has done")
            }
        }


        return mergeSlices()
    }

    private fun mergeSlices(): File {
        val file = downloadSpace.outputFile()
        if (file.exists()) {
            file.delete()
            file.createNewFile()
        }
        val channel = FileOutputStream(file).channel
        val sliceFiles = mutableListOf<File>()
        sliceKeys().sorted()
            .forEach {
                DLog.log("write $it")
                val sliceFile = File(downloadSpace.baseDir, it)
                sliceFiles.add(sliceFile)
                val sliceChannel = FileInputStream(sliceFile).channel
                sliceChannel.transferTo(0, sliceChannel.size(), channel)
            }
        sliceFiles.forEach {
            it.delete()
        }
        return file
    }

    override fun calculateDownloadedBytes(): Long {
        if (!supportRange()) {
            return 0L
        }
        val sliceKeys = sliceKeys()
        if (sliceKeys.isEmpty()) {
            return 0
        }
        var downloaded = 0L
        sliceKeys.forEach {
            val sliceSize = downloadSpace.getProp(it)?.split("/")?.get(1)?.toLongOrDefault(0L)
            if (sliceSize != null) {
                downloaded += sliceSize
            }
        }

        return downloaded
    }


    private fun detectDownloadInfo() {
        var totalLength = totalLength()
        if (totalLength == 0L) {
            val response = DownloadClient.get(url)
            if (response.code == HttpURLConnection.HTTP_OK) {
                totalLength = response.header("Content-Length", "0")!!.toLongOrDefault(0L)
                val fileName = response.header("Content-Disposition", null)?.let {
                    it.substring(
                        it.indexOf("filename=") + 9
                    )
                }


                val range = response.header("Accept-Ranges", null)
                DLog.log(response.headers.toString())

                val supportRange = when {
                    "none".equals(range, ignoreCase = true) -> {
                        //不支持range
                        false
                    }
                    range == null -> {
                        //可能不支持
                        val rangeDetect = DownloadClient.getRange(url, 0, 10)
                        val code = rangeDetect.code
                        rangeDetect.closeQuietly()
                        code == HttpURLConnection.HTTP_PARTIAL
                    }
                    else -> {
                        true
                    }
                }
                DLog.log("fileName=$fileName")
                saveDownloadInfo(fileName ?: "base.apk", totalLength, supportRange)
            }

            response.close()
        }

    }

    /**
     * 返回null不支持切片
     */
    private fun getNotFinishedSlices(): List<Slice> {
        val totalLength = totalLength()
        if (totalLength == 0L) {
            throw UnknownException("get slice when len=0")
        }
        val sliceKeys = sliceKeys().toMutableList()
        if (sliceKeys.isEmpty()) {
            //创建切片
            val sliceCount = totalLength / PAGE_SIZE
            var start = 0L
            DLog.log("len=$totalLength count=$sliceCount")
            for (i in 0 until sliceCount) {
                val sliceSize = if (i == sliceCount - 1) {
                    PAGE_SIZE + totalLength % PAGE_SIZE
                } else {
                    PAGE_SIZE
                }
                val sliceKey = "slice_%04d".format(i)
                putSlice(sliceKey, start, 0, sliceSize)
                start += sliceSize
                sliceKeys.add(sliceKey)
            }
            downloadSpace.saveProperties()
        }
        val notFinishedSliceList = mutableListOf<Slice>()

        sliceKeys.forEach { key ->
            val pValue = downloadSpace.getProp(key)!!
            val property = pValue.split("/").map { p_item ->
                p_item.toLong()
            }
            DLog.log("add slices $pValue ")
            if (property[1] < property[2]) {
                notFinishedSliceList += Slice(key, property[0], property[1], property[2])
            } else if (property[1] > property[2]) {
                throw UnknownException("wrong slice!!$key=$pValue")
            }
        }

        return notFinishedSliceList

    }

    private fun sliceKeys(): List<String> {
        return downloadSpace.propKeys().filter { it.startsWith(P_KEY_SLICE_PREFIX) }
    }

    private fun putSlice(
        key: String,
        start: Long,
        downloaded: Long,
        pageSize: Long
    ) {
        downloadSpace.putProp(key, "$start/$downloaded/$pageSize")
    }

    private fun download(): File {

        val file = downloadSpace.outputFile()
        if (file.exists()) {
            file.delete()
        }
        val randomAccessFile = RandomAccessFile(file, "rw")
        val totalSize = totalLength()
        randomAccessFile.setLength(totalSize)
        val response = DownloadClient.get(url)
        if (response.code != HttpURLConnection.HTTP_OK) {
            throw NetworkException("code=${response.code}")
        }
        writerStream(response, randomAccessFile) {
            publishProgress(it)
        }
        return file
    }


    private fun writerStream(
        response: Response,
        randomAccessFile: RandomAccessFile,
        progress: (Long) -> Unit
    ) {
        val byteStream = response.body!!.byteStream()
        var readLen: Int
        val buffer = ByteArray(kb(10).toInt())
        var downloaded = 0L
        while (byteStream.read(buffer).also { readLen = it } != -1) {
            randomAccessFile.write(buffer, 0, readLen)
            downloaded += readLen
            progress.invoke(downloaded)
            if (isCancelRequested) {
                response.closeQuietly()
                throw CancelException()
            }
        }
    }

    data class Slice(
        val key: String,
        val start: Long,
        var downloaded_size: Long,
        val len: Long
    )

    internal inner class SliceWorker(var workDir: File, var slice: Slice) : DownloadWork() {

        override fun source(): AbstractSource {
            return this@UrlSource
        }

        override fun run() {
            try {
                val sliceFile = File(workDir, slice.key)
                val randomAccessFile = RandomAccessFile(sliceFile, "rw")
                if (randomAccessFile.length() == 0L) {
                    randomAccessFile.setLength(slice.len)
                }
                randomAccessFile.seek(slice.downloaded_size)
                val startOffset = slice.start + slice.downloaded_size
                val endPosition = slice.start + slice.len - 1
                val rangeResponse = DownloadClient.getRange(url, startOffset, endPosition)
                if (rangeResponse.code != HttpURLConnection.HTTP_PARTIAL) {
                    throw UnknownException("Range not support")
                }
                val alreadyDownloaded = slice.downloaded_size
                writerStream(rangeResponse, randomAccessFile) {
                    slice.downloaded_size = alreadyDownloaded + it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            isFinished = true
        }


    }
}