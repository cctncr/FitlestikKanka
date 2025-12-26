package com.example.fitlestikkanka.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.fitlestikkanka.database.AppDatabase
import org.koin.dsl.module

/**
 * iOS-specific platform module.
 * Provides iOS SQLDelight driver.
 */
actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = AppDatabase.Schema,
            name = "app_database.db"
        )
    }
}
