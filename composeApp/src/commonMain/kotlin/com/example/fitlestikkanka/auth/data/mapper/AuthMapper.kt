package com.example.fitlestikkanka.auth.data.mapper

import com.example.fitlestikkanka.auth.data.datasource.remote.dto.LoginResponseDto
import com.example.fitlestikkanka.auth.data.datasource.remote.dto.UserDto
import com.example.fitlestikkanka.auth.domain.model.AuthToken
import com.example.fitlestikkanka.auth.domain.model.User
import kotlinx.datetime.Instant

/**
 * Mapper between auth DTOs and domain models.
 */
object AuthMapper {
    /**
     * Map LoginResponseDto to AuthToken domain model.
     */
    fun loginResponseToDomain(dto: LoginResponseDto): AuthToken {
        return AuthToken(
            accessToken = dto.accessToken,
            tokenType = dto.tokenType
        )
    }

    /**
     * Map UserDto to User domain model.
     */
    fun userDtoToDomain(dto: UserDto): User {
        return User(
            id = dto.id,
            username = dto.username,
            email = dto.email,
            createdAt = Instant.parse(dto.createdAt)
        )
    }
}
