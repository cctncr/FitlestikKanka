package com.example.fitlestikkanka.core.di

import com.example.fitlestikkanka.debts.data.datasource.remote.DebtsApiService
import com.example.fitlestikkanka.debts.data.datasource.remote.DebtsApiServiceImpl
import com.example.fitlestikkanka.debts.data.repository.DebtsRepositoryImpl
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository
import com.example.fitlestikkanka.debts.domain.usecase.LoadDebtsUseCase
import com.example.fitlestikkanka.debts.presentation.viewmodel.DebtsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin DI module for Debts feature.
 *
 * Provides all dependencies for debts functionality:
 * - Data sources (API services)
 * - Repositories
 * - Use cases
 * - ViewModels
 */
val debtsModule = module {

    // Data Sources - Remote
    single<DebtsApiService> {
        DebtsApiServiceImpl(httpClient = get())
    }

    // Repositories
    single<DebtsRepository> {
        DebtsRepositoryImpl(apiService = get())
    }

    // Use Cases
    factory {
        LoadDebtsUseCase(debtsRepository = get())
    }

    // ViewModels
    viewModel {
        DebtsViewModel(loadDebtsUseCase = get())
    }
}
