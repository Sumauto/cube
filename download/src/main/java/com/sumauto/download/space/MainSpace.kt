package com.sumauto.download.space

import com.sumauto.download.DLog
import com.sumauto.download.DirConflictStrategy
import com.sumauto.download.exceptions.DownloadException
import com.sumauto.download.md5
import com.sumauto.download.space.AbsWorkSpace
import java.io.File


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/08 
 */

internal class MainSpace(dir: File) : AbsWorkSpace(dir) {


    internal fun createDownloadDir(url: String): File {
        val downloadKey= md5(url)
        val path = getProp(downloadKey)
        val file:File
        if (path == null) {
             file = File(baseDir, downloadKey)
            file.mkdir()
            putProp(downloadKey, file.absolutePath)
            saveProperties()
        }else{
            file= File(path)
            if (!file.exists()){
                file.mkdirs()
            }
        }
        return file
    }

    internal fun getDownloadDir(url: String): String? {
        return getProp(md5(url))
    }

    internal fun registerDownloadDir(url:String,dir: File,strategy: DirConflictStrategy){
        DLog.log("registerDownloadDir:$dir strategy:$strategy")

        val downloadKey= md5(url)
        val existsPath=getProp(downloadKey)
        if (existsPath==null){
            putAndSaveProp(downloadKey,dir.absolutePath)
            return
        }
        if (existsPath!=dir.absolutePath){
            val existsDir = File(existsPath)
            if (existsDir.exists()){
                when (strategy){
                    DirConflictStrategy.DELETE->{
                        existsDir.deleteRecursively()
                        putAndSaveProp(downloadKey,dir.absolutePath)
                    }

                    DirConflictStrategy.FAIL->{
                        throw DownloadException("$url already registered in $existsPath")
                    }
                    DirConflictStrategy.CREATE->{
                        putAndSaveProp(downloadKey,dir.absolutePath)
                    }

                    DirConflictStrategy.USE_OLD->{
                        return
                    }
                }

            }
        }

    }
}