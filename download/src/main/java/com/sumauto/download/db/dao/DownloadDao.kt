package com.sumauto.download.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sumauto.download.db.entity.DownloadInfo
import kotlinx.coroutines.flow.Flow


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/12 
 */
@Dao
abstract class DownloadDao {

    @Query("select * from downloadinfo where id=:id")
    abstract fun getDownloadInfo(id: Long): DownloadInfo?

    @Query("select * from downloadinfo where url=:url")
    abstract fun getDownloadInfo(url: String): DownloadInfo?

    @Query("select * from downloadinfo where url=:url")
    abstract fun getDownloadFlow(url: String): Flow<DownloadInfo?>


    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insert(info: DownloadInfo): Long

    @Update
    abstract fun update(info: DownloadInfo)

    @Query("update downloadinfo set downloaded=:downloaded and total=:total where id=:id")
    abstract fun updateProgress(id: Long, downloaded: Long, total: Long)


    @Query("delete from downloadinfo where id=:id")
    abstract fun delete(id: Long)

}