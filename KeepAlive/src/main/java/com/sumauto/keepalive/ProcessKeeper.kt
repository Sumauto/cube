package com.sumauto.keepalive

import android.app.Application
import android.content.Intent
import com.sumauto.keepalive.deamon.*
import com.sumauto.keepalive.process.DLaunchService
import com.sumauto.keepalive.process.ResidentService
import com.sumauto.keepalive.utils.ServiceHelper
import com.sumauto.keepalive.utils.SystemUtils
import com.sumauto.keepalive.utils.XLog
import java.io.File

object ProcessKeeper {

    fun attach(application: Application) {
        val processName = SystemUtils.getProcessName()
        XLog.d("call attach on $processName")

        if (SystemUtils.isMainProcess(application)) {
            ServiceHelper.bind(application, ResidentService::class.java)
            return
        }

        if (processName?.contains("sumauto_") == true) {
            val lockFile =
                File(application.filesDir, processName.replace("${application.packageName}:", ""))

            val appInfo = application.applicationInfo
            val si = StartInfo()
            si.nativeLibraryDir = appInfo.nativeLibraryDir
            si.publicSourceDir = appInfo.publicSourceDir
            si.processName = SystemUtils.getProcessName()
            si.serviceIntent = Intent(application, DLaunchService::class.java)
            si.insIntent = Intent(application, MyInstrumentation::class.java)
            //application.startInstrumentation(si.insIntent.component!!,null,null)
            FileLocker.startLock(lockFile, si)


            AppProcessRunner.startAppProcess(si, "daemon_s", lockFile.absolutePath)
        }


    }

}