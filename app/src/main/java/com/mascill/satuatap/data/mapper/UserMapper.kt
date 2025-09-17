package com.mascill.satuatap.data.mapper

import com.mascill.satuatap.data.local.entity.UserEntity
import com.mascill.satuatap.data.model.User
import com.mascill.satuatap.data.remote.dto.UserDto

// Domain Model <-> Entity
fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

// Domain Model <-> DTO
fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}