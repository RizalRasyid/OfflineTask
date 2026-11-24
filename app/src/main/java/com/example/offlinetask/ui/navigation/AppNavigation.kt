
package com.example.offlinetask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.offlinetask.data.local.TaskDatabase
import com.example.offlinetask.data.repository.TaskRepository
import com.example.offlinetask.ui.viewmodel.TaskViewModel
import com.example.offlinetask.ui.viewmodel.TaskViewModelFactory
import com.example.offlinetask.ui.screens.TaskListScreen
import com.example.offlinetask.ui.screens.AddTaskScreen
import com.example.offlinetask.ui.screens.TaskDetailScreen
import com.example.offlinetask.ui.screens.EditTaskScreen
import androidx.lifecycle.viewmodel.compose.viewModel

object AppRoute {
    const val TASK_LIST = "task_list"
    const val ADD_TASK = "add_task"
    const val TASK_DETAIL = "task_detail/{taskId}"
    const val EDIT_TASK = "edit_task/{taskId}"

    fun detail(taskId: Int) = "task_detail/$taskId"
    fun edit(taskId: Int) = "edit_task/$taskId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = androidx.compose.ui.platform.LocalContext.current

    val database = remember {
        TaskDatabase.getDatabase(context)
    }

    val repository = remember {
        TaskRepository(database.taskDao())
    }

    val factory = remember {
        TaskViewModelFactory(repository)
    }

    val taskViewModel: TaskViewModel = viewModel(
        factory = factory
    )

    NavHost(
        navController = navController,
        startDestination = AppRoute.TASK_LIST
    ) {
        composable(AppRoute.TASK_LIST) {
            TaskListScreen(
                viewModel = taskViewModel,
                onAddClick = {
                    navController.navigate(AppRoute.ADD_TASK)
                },
                onTaskClick = { taskId ->
                    navController.navigate(
                        AppRoute.detail(taskId)
                    )
                }
            )
        }

        composable(AppRoute.ADD_TASK) {
            AddTaskScreen(
                viewModel = taskViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AppRoute.TASK_DETAIL,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

            TaskDetailScreen(
                taskId = taskId,
                viewModel = taskViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { id ->
                    navController.navigate(AppRoute.edit(id))
                }
            )
        }

        composable(
            route = AppRoute.EDIT_TASK,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

            EditTaskScreen(
                taskId = taskId,
                viewModel = taskViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
