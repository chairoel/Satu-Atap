package com.mascill.satuatap.data.repository

import com.mascill.satuatap.data.model.User
import com.mascill.satuatap.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserById(userId: String): Flow<Resource<User>>

    fun getAllUsers(): Flow<Resource<List<User>>>

    suspend fun createUser(user: User): Resource<User>

    suspend fun updateUser(user: User): Resource<User>

    suspend fun deleteUser(userId: String): Resource<Unit>

    suspend fun syncUsers(): Resource<Unit>
}