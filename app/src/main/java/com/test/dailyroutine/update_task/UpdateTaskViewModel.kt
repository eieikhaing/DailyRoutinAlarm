package com.test.dailyroutine.update_task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.test.dailyroutine.database.ToDoDao
import com.test.dailyroutine.database.ToDoTaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateTaskViewModel(
    private val database: ToDoDao,
    application: Application
) : AndroidViewModel(application) {

    fun updateTask(
        taskTitle: String,
        taskDesc: String,
        taskDate: Long,
        taskTime: Long,
        notiTime: String,
        notiId: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            updateTodoTask(taskTitle, taskDesc, taskDate, taskTime, notiTime, notiId)
        }
    }

     private suspend fun updateTodoTask( taskTitle: String,
        taskDesc: String,
        taskDate: Long,
        taskTime: Long,
        notiTime: String,
        notiId: Int) {
          database.updateTask(taskTitle, taskDesc, taskDate, taskTime, notiTime, notiId)

     }

}