package com.example.fitlestikkanka.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.fitlestikkanka.database.AppDatabase
import org.koin.dsl.module

/**
 * JVM/Desktop-specific platform module.
 * Provides JVM SQLDelight driver.
 */
actual val platformModule = module {
    single<SqlDriver> {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppDatabase.Schema.create(driver)
        driver
    }
}
