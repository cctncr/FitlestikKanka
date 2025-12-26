package com.example.fitlestikkanka.auth.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.example.fitlestikkanka.auth.domain.model.AuthToken
import com.example.fitlestikkanka.auth.domain.model.User
import kotlinx.datetime.Instant

/**
 * Android implementation of AuthLocalDataSource using SharedPreferences.
 *
 * Persists authentication data in encrypted SharedPreferences.
 */
class AuthLocalDataSourceImpl(
    private val context: Context
) : AuthLocalDataSource {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_TOKEN_TYPE = "token_type"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_CREATED_AT = "created_at"
    }

    override suspend fun saveToken(token: AuthToken) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, token.accessToken)
            .putString(KEY_TOKEN_TYPE, token.tokenType)
            .apply()
    }

    override suspend fun getToken(): AuthToken? {
        val accessToken = prefs.getString(KEY_ACCESS_TOKEN, null) ?: return null
        val tokenType = prefs.getString(KEY_TOKEN_TYPE, "bearer") ?: "bearer"

        return AuthToken(
            accessToken = accessToken,
            tokenType = tokenType
        )
    }

    override suspend fun clearToken() {
        prefs.edit()
            .remove(KEY_ACCESS_TOKEN)
            .remove(KEY_TOKEN_TYPE)
            .apply()
    }

    override suspend fun saveCurrentUser(user: User) {
        prefs.edit()
            .putInt(KEY_USER_ID, user.id)
            .putString(KEY_USERNAME, user.username)
            .putString(KEY_EMAIL, user.email)
            .putString(KEY_CREATED_AT, user.createdAt.toString())
            .apply()
    }

    override suspend fun getCurrentUser(): User? {
        val userId = prefs.getInt(KEY_USER_ID, -1)
        if (userId == -1) return null

        val username = prefs.getString(KEY_USERNAME, null) ?: return null
        val email = prefs.getString(KEY_EMAIL, null) ?: return null
        val createdAtString = prefs.getString(KEY_CREATED_AT, null) ?: return null

        return User(
            id = userId,
            username = username,
            email = email,
            createdAt = Instant.parse(createdAtString)
        )
    }

    override suspend fun clearCurrentUser() {
        prefs.edit()
            .remove(KEY_USER_ID)
            .remove(KEY_USERNAME)
            .remove(KEY_EMAIL)
            .remove(KEY_CREATED_AT)
            .apply()
    }
}
