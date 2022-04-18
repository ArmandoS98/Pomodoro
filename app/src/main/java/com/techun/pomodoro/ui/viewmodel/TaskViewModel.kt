package com.techun.pomodoro.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.pomodoro.domain.DeleteTaskUseCase
import com.techun.pomodoro.domain.GetTasksUseCase
import com.techun.pomodoro.domain.InsertTaskUseCase
import com.techun.pomodoro.domain.TaskItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasks: GetTasksUseCase,
    private val insertTask: InsertTaskUseCase,
    private val deleteTasks: DeleteTaskUseCase
) : ViewModel() {
    val taskModel = MutableLiveData<List<TaskItem>>()
    val insertTaskModel = MutableLiveData<Long>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() = viewModelScope.launch {
        val result = getTasks()
        if (!result.isNullOrEmpty()) {
            taskModel.postValue(result!!)
        }
    }

    fun insertTask(task: TaskItem) {
        viewModelScope.launch {
            val result = insertTask.invoke(task)
            insertTaskModel.postValue(result)
        }
    }
}