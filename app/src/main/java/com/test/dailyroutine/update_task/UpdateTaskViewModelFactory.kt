package com.test.dailyroutine.update_task

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.dailyroutine.create_task.CreateTaskViewModel
import com.test.dailyroutine.database.ToDoDao

class UpdateTaskViewModelFactory(
    private val dataSource: ToDoDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateTaskViewModel::class.java)) {
            return UpdateTaskViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}