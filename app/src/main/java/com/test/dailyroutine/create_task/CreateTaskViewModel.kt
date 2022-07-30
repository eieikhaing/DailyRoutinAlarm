package com.test.dailyroutine.create_task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.test.dailyroutine.database.ToDoDao
import com.test.dailyroutine.database.ToDoTaskModel
import kotlinx.coroutines.launch

class CreateTaskViewModel(
    private val database: ToDoDao,
    application: Application
) : AndroidViewModel(application) {

    fun createTask(
        taskTitle: String,
        taskDesc: String,
        taskDate: Long,
        taskTime: Long,
        notiTime: String
    ) {
        viewModelScope.launch {
            val newTask = ToDoTaskModel(taskTitle, taskDesc, taskDate, taskTime,notiTime)
            insert(newTask)

        }

    }

    private suspend fun insert(newTask: ToDoTaskModel) {
        database.insertTask(newTask)
    }

}