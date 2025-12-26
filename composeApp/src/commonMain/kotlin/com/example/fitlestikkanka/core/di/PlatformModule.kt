package com.example.fitlestikkanka.core.di

import org.koin.core.module.Module

/**
 * Platform-specific module that provides SqlDriver.
 * Actual implementations are in platform source sets:
 * - androidMain: AndroidSqliteDriver
 * - iosMain: NativeSqliteDriver
 * - jvmMain: JdbcSqliteDriver
 */
expect val platformModule: Module
