package com.example.fitlestikkanka.chat.data

import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageDto
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository

/**
 * Handles AI classification results from backend.
 *
 * Routes classified messages to appropriate repositories:
 * - TASK → Refreshes tasks list
 * - EXPENSE → Refreshes debts/balance
 * - NORMAL → No action (regular chat message)
 *
 * Backend creates the actual task/debt entries; this handler just
 * triggers UI refresh so the new items appear.
 */
class AiClassificationHandler(
    private val tasksRepository: TasksRepository,
    private val debtsRepository: DebtsRepository
) {
    /**
     * Process AI classification result and trigger appropriate actions.
     *
     * @param message MessageDto with potential AI analysis
     */
    suspend fun handleClassification(message: MessageDto) {
        val analysis = message.aiAnalysis ?: return

        when (analysis.classification.uppercase()) {
            "TASK" -> {
                // Backend already created task via API
                // Just refresh tasks list to show new task in UI
                tasksRepository.loadTasks()
                    .onFailure { error ->
                        println("Failed to refresh tasks after AI classification: ${error.message}")
                    }
            }

            "EXPENSE" -> {
                // Backend already created debt/expense via API
                // Refresh debts balance to show updated amounts
                debtsRepository.loadBalance()
                    .onFailure { error ->
                        println("Failed to refresh debts after AI classification: ${error.message}")
                    }
            }

            "NORMAL" -> {
                // Regular chat message, no action needed
            }

            else -> {
                // Unknown classification type, log and ignore
                println("Unknown AI classification type: ${analysis.classification}")
            }
        }
    }
}
