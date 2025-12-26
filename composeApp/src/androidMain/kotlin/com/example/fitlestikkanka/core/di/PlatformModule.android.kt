package com.example.fitlestikkanka.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.fitlestikkanka.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Android-specific platform module.
 * Provides Android SQLDelight driver.
 */
actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            AppDatabase.Schema,
            androidContext(),
            "app_database.db"
        )
    }
}
