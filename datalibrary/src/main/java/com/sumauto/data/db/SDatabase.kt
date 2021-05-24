package com.sumauto.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sumauto.data.db.dao.EventDao
import com.sumauto.data.db.entity.Event

@Database(
    entities = [
        Event::class
    ],
    version = 1
)
abstract class SDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}