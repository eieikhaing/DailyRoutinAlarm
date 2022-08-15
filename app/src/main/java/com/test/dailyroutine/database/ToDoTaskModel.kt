package com.test.dailyroutine.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "to_do_table")
data class ToDoTaskModel(

    @ColumnInfo(name = "task_title")
    var taskTitle: String,

    @ColumnInfo(name = "task_description")
    var taskDesc: String,

    @ColumnInfo(name = "task_date")
    var taskDate: Long,

    @ColumnInfo(name = "task_time")
    var taskTime: Long,

    @ColumnInfo(name = "noti_time")
    var notiTime: String,

    @ColumnInfo(name = "noti_id")
    var notiId: Int = 0,

    @ColumnInfo(name = "task_status")
    var taskStatus: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

) : Serializable
