package com.test.dailyroutine.task_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.dailyroutine.database.ToDoDao

class TaskListViewModelFactory(
    private val dataSource: ToDoDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            return TaskListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}