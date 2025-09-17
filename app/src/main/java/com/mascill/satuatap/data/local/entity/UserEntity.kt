package com.mascill.satuatap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val profileImageUrl: String,
    val createdAt: Long,
    val updatedAt: Long
)