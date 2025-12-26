package com.example.fitlestikkanka.core.di

import app.cash.sqldelight.db.SqlDriver
import com.example.fitlestikkanka.database.AppDatabase
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Core application module providing infrastructure dependencies.
 * Includes:
 * - HttpClient for networking
 * - SQLDelight database
 */
val appModule = module {
    // Ktor HttpClient with WebSocket support
    single {
        HttpClient {
            install(WebSockets)
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

    // SQLDelight Database
    // Driver is provided platform-specifically
    single {
        AppDatabase(driver = get<SqlDriver>())
    }
}
