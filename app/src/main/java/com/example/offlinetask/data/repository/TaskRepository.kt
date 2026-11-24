
package com.example.offlinetask.data.repository

import com.example.offlinetask.data.local.TaskDao
import com.example.offlinetask.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao
) {
    val allTasks: Flow<List<TaskEntity>> =
        taskDao.getAllTasks()

    fun getTaskById(id: Int): Flow<TaskEntity?> {
        return taskDao.getTaskById(id)
    }

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}
