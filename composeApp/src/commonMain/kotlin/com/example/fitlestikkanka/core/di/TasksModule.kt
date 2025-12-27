package com.example.fitlestikkanka.core.di

import com.example.fitlestikkanka.tasks.data.datasource.remote.TasksApiService
import com.example.fitlestikkanka.tasks.data.datasource.remote.TasksApiServiceImpl
import com.example.fitlestikkanka.tasks.data.repository.TasksRepositoryImpl
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository
import com.example.fitlestikkanka.tasks.domain.usecase.DeleteTaskUseCase
import com.example.fitlestikkanka.tasks.domain.usecase.LoadTasksUseCase
import com.example.fitlestikkanka.tasks.domain.usecase.UpdateTaskStatusUseCase
import com.example.fitlestikkanka.tasks.presentation.viewmodel.TasksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin DI module for Tasks feature.
 *
 * Provides all dependencies for tasks functionality:
 * - Data sources (API services)
 * - Repositories
 * - Use cases
 * - ViewModels
 */
val tasksModule = module {

    // Data Sources - Remote
    single<TasksApiService> {
        TasksApiServiceImpl(httpClient = get(), authRepository = get())
    }

    // Repositories
    single<TasksRepository> {
        TasksRepositoryImpl(apiService = get())
    }

    // Use Cases
    factory {
        LoadTasksUseCase(tasksRepository = get())
    }

    factory {
        UpdateTaskStatusUseCase(tasksRepository = get())
    }

    factory {
        DeleteTaskUseCase(tasksRepository = get())
    }

    // ViewModels
    viewModel {
        TasksViewModel(
            loadTasksUseCase = get(),
            updateTaskStatusUseCase = get(),
            deleteTaskUseCase = get(),
            tasksRepository = get()
        )
    }
}
