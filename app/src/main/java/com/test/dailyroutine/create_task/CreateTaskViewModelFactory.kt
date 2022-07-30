package com.test.dailyroutine.create_task

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.dailyroutine.database.ToDoDao

class CreateTaskViewModelFactory(
    private val dataSource: ToDoDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTaskViewModel::class.java)) {
            return CreateTaskViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}