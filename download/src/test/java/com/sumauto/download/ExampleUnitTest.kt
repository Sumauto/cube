package com.sumauto.download

import org.junit.Test

import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Downloader.init(
            DownloadConfig(
                workDir = File("work_dir").absolutePath
            )
        )
        Downloader.start("https://c2daa83519de8ac3b2e97e975a7dd2fa.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/0989826B43B457EBB24626422C9EED9E.apk?mkey=60ff8712de5f48ee&f=0ee9&fsname=com.wodidashi.avalon_10.21.2_160202.apk&csr=1bbd&cip=222.95.110.27&proto=https")
    }
}