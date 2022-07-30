package com.test.dailyroutine.task_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.dailyroutine.database.ToDoDao
import com.test.dailyroutine.database.ToDoTaskModel

class TaskListViewModel (
    private val database: ToDoDao,
    application: Application
) : AndroidViewModel(application) {
    private var _responseTaskList =
        MutableLiveData<List<ToDoTaskModel>>()
    var responseTaskList : LiveData<List<ToDoTaskModel>>? = null


    fun getAllTaskList() {
        responseTaskList = database.getTask()
    }

}