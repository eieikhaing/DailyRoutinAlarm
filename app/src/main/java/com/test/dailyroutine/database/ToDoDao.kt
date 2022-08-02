package com.test.dailyroutine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(newTask: ToDoTaskModel): Long

    /*@Update
    suspend fun updateTask(newTask: ToDoTaskModel)*/

    @Query("Select * from to_do_table")
    fun getTask(): LiveData<List<ToDoTaskModel>>

    /*@Query("Update to_do_table Set task_status = 1 where id=:uid")
    fun finishTask(uid:Long)*/
    @Query("Update to_do_table Set task_title = :taskTitle,task_description =:taskDesc, task_date = :taskDate, " +
            "task_time=:taskTime, noti_time=:notiTime where noti_id=:notiId")
    fun updateTask(
        taskTitle: String,
        taskDesc: String,
        taskDate: Long,
        taskTime: Long,
        notiTime: String,
        notiId: Int
    )

    @Query("Delete from to_do_table where noti_id=:uid")
    fun deleteTask(uid: Long)

    @Query("Select noti_time from to_do_table where id=:uid")
    fun getNotiId(uid: Long): String

}