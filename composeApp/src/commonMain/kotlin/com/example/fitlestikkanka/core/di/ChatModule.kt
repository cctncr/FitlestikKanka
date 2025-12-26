package com.example.fitlestikkanka.core.di

import com.example.fitlestikkanka.chat.data.datasource.local.MessageLocalDataSource
import com.example.fitlestikkanka.chat.data.datasource.local.MessageLocalDataSourceImpl
import com.example.fitlestikkanka.chat.data.datasource.remote.WebSocketClient
import com.example.fitlestikkanka.chat.data.datasource.remote.WebSocketClientImpl
import com.example.fitlestikkanka.chat.data.repository.ConversationRepositoryImpl
import com.example.fitlestikkanka.chat.data.repository.MessageRepositoryImpl
import com.example.fitlestikkanka.chat.domain.repository.ConversationRepository
import com.example.fitlestikkanka.chat.domain.repository.MessageRepository
import com.example.fitlestikkanka.chat.domain.usecase.LoadConversationUseCase
import com.example.fitlestikkanka.chat.domain.usecase.ObserveMessagesUseCase
import com.example.fitlestikkanka.chat.domain.usecase.SendMessageUseCase
import com.example.fitlestikkanka.chat.domain.usecase.UpdateMessageStatusUseCase
import com.example.fitlestikkanka.chat.presentation.viewmodel.ChatViewModel
import com.example.fitlestikkanka.core.util.MessageGrouper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin dependency injection module for the chat feature.
 * Configures all dependencies for Domain, Data, and Presentation layers.
 *
 * Dependencies are organized by layer:
 * - Data Sources (local and remote)
 * - Repositories
 * - Use Cases
 * - Utilities
 * - ViewModels
 */
val chatModule = module {
    // Data Sources - Local
    single<MessageLocalDataSource> {
        MessageLocalDataSourceImpl(database = get())
    }

    // Data Sources - Remote
    single<WebSocketClient> {
        WebSocketClientImpl(httpClient = get())
    }

    // Repositories
    single<MessageRepository> {
        MessageRepositoryImpl(
            localDataSource = get(),
            webSocketClient = get(),
            currentUserId = "current-user" // TODO: Get from auth module/session
        )
    }

    single<ConversationRepository> {
        ConversationRepositoryImpl()
    }

    // Use Cases
    factory {
        SendMessageUseCase(messageRepository = get())
    }

    factory {
        ObserveMessagesUseCase(messageRepository = get())
    }

    factory {
        UpdateMessageStatusUseCase(messageRepository = get())
    }

    factory {
        LoadConversationUseCase(conversationRepository = get())
    }

    // Utilities
    single {
        MessageGrouper()
    }

    // ViewModels
    // Parameters: conversationId and currentUserId passed from UI
    viewModel { (conversationId: String, currentUserId: String) ->
        ChatViewModel(
            conversationId = conversationId,
            currentUserId = currentUserId,
            sendMessageUseCase = get(),
            observeMessagesUseCase = get(),
            updateMessageStatusUseCase = get(),
            loadConversationUseCase = get(),
            messageGrouper = get()
        )
    }
}
