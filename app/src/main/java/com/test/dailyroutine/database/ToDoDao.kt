package com.test.dailyroutine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(newTask: ToDoTaskModel):Long

    @Query("Select * from to_do_table")
    fun getTask():LiveData<List<ToDoTaskModel>>

    @Query("Update to_do_table Set task_status = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from to_do_table where id=:uid")
    fun deleteTask(uid:Long)

    @Query("Select noti_time from to_do_table where id=:uid")
    fun getNotiId(uid:Long):String

}