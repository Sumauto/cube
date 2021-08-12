package com.sumauto.download.db.entity

import androidx.room.Entity


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
@Entity
data class DownloadTask(
    val id: Long,//任务id
    val download_id: Long,//关联下载id
    val url: String,//下载url
    val start_offset:Long=0,//相对完整文件的下载偏移量
    val total: Long = 0,//下载大小
    val downloaded: Long = 0,//已下载
    val slice_index: Int,//切片索引
    val finished: Boolean//是否已完成
)