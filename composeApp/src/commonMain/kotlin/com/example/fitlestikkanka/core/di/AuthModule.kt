package com.example.fitlestikkanka.core.di

import com.example.fitlestikkanka.auth.data.datasource.remote.AuthApiService
import com.example.fitlestikkanka.auth.data.datasource.remote.AuthApiServiceImpl
import com.example.fitlestikkanka.auth.data.repository.AuthRepositoryImpl
import com.example.fitlestikkanka.auth.domain.repository.AuthRepository
import com.example.fitlestikkanka.auth.domain.usecase.GetCurrentUserUseCase
import com.example.fitlestikkanka.auth.domain.usecase.LoginUseCase
import com.example.fitlestikkanka.auth.presentation.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin DI module for Authentication feature.
 *
 * Provides all dependencies for auth functionality:
 * - Data sources (API services, local storage)
 * - Repositories
 * - Use cases
 * - ViewModels
 *
 * Note: AuthLocalDataSource is provided platform-specifically in platformModule
 */
val authModule = module {

    // Data Sources - Remote
    single<AuthApiService> {
        AuthApiServiceImpl(httpClient = get())
    }

    // Note: AuthLocalDataSource provided in platformModule (platform-specific)

    // Repositories
    single<AuthRepository> {
        AuthRepositoryImpl(
            apiService = get(),
            localDataSource = get()
        )
    }

    // Use Cases
    factory {
        LoginUseCase(authRepository = get())
    }

    factory {
        GetCurrentUserUseCase(authRepository = get())
    }

    // ViewModels
    viewModel {
        AuthViewModel(
            loginUseCase = get(),
            getCurrentUserUseCase = get(),
            webSocketClient = get()
        )
    }
}
