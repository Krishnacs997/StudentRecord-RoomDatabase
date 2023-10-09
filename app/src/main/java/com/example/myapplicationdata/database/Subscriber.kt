package com.example.myapplicationdata.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriber_name_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String
)
