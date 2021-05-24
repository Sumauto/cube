package com.sumauto.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true)
    var id:Int
)
