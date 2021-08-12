package com.sumauto.download.db

import android.content.Context
import androidx.room.*
import com.sumauto.download.DownloadProvider
import com.sumauto.download.db.dao.DownloadDao
import com.sumauto.download.db.entity.DownloadInfo


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/07/23 
 */
@Database(
    entities = [DownloadInfo::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun get(): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    DownloadProvider.appContext!!,
                    AppDataBase::class.java,
                    "donut_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun downloadDao(): DownloadDao
}