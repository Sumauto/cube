package com.sumauto.download

import org.junit.Test

import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
const val URL =
    "https://c2daa83519de8ac3b2e97e975a7dd2fa.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/0989826B43B457EBB24626422C9EED9E.apk?mkey=60ff8712de5f48ee&f=0ee9&fsname=com.wodidashi.avalon_10.21.2_160202.apk&csr=1bbd&cip=222.95.110.27&proto=https"

class ExampleUnitTest {

    @Test
    fun testDownload() {
        Downloader.init(
            DownloadConfig(
                workDir = File("work_dir").absolutePath
            )
        )
        doDownload(false, Downloader.Options(
            downloadDir = File("123/1.0.1"),
            //dirConflictStrategy = DirConflictStrategy.FAIL
        ))
        doDownload(false, Downloader.Options(
            downloadDir = File("123/1.0.2"),
            //dirConflictStrategy = DirConflictStrategy.USE_OLD
        ))
        doDownload(false, Downloader.Options(
            downloadDir = File("123/1.0.3"),
            //dirConflictStrategy = DirConflictStrategy.CREATE
        ))

        doDownload(false, Downloader.Options(
            downloadDir = File("123/1.0.4"),
            //dirConflictStrategy = DirConflictStrategy.DELETE
        ))


    }

    fun doDownload(doCancel: Boolean, options: Downloader.Options) {


        val handler = Downloader.create(
            URL,options
        )
        handler.addDownloadListener(object : DownloadHandler.DownloadListener {
            override fun onSuccess(file: File) {
                println("success:$file")
            }

            override fun onError(e: Exception) {
                println("error:${e.localizedMessage}")
            }

            override fun onCancel() {
                println("onCancel")
            }

        })

        handler.addProgressListener(object : DownloadHandler.DownloadProgressListener {
            override fun onProgressUpdate(downloaded: Long, total: Long) {
                println("onProgressUpdate:${progress(downloaded, total)}%")
            }
        })
        if (doCancel) {

            Thread {
                Thread.sleep(2000)
                handler.cancel()
            }.start()
        }

        val result = handler.fetch()
        if (result.successFile != null) {
            println("fetch success:${result.successFile}")
        } else {
            println("fetch failed:${result.exception}")
        }
    }
}