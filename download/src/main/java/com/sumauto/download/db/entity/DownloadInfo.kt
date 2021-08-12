package com.sumauto.download.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
@Entity(
    indices = [Index(
        value = ["url"],
        unique = true
    )]
)
data class DownloadInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    var url: String="",
    var workDir: String="",
    var outputPath: String?=null,
    var downloaded: Long = 0,
    val total: Long = 0,
    val supportRange: Boolean = true,
    val fileName: String="",
    val complete: Boolean = false
)
