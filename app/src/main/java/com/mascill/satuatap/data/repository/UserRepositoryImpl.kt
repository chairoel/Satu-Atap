package com.mascill.satuatap.data.repository

import com.mascill.satuatap.data.local.dao.UserDao
import com.mascill.satuatap.data.mapper.toDomain
import com.mascill.satuatap.data.mapper.toDto
import com.mascill.satuatap.data.mapper.toEntity
import com.mascill.satuatap.data.model.User
import com.mascill.satuatap.data.remote.api.ApiService
import com.mascill.satuatap.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override fun getUserById(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        try {
            // Try to get from local first
            userDao.getUserById(userId).collect { localUser ->
                if (localUser != null) {
                    emit(Resource.Success(localUser.toDomain()))
                }

                // Then fetch from remote
                try {
                    val response = apiService.getUserById(userId)
                    if (response.success && response.data != null) {
                        val user = response.data.toDomain()
                        userDao.insertUser(user.toEntity())
                        emit(Resource.Success(user))
                    } else {
                        if (localUser == null) {
                            emit(Resource.Error(response.message ?: "User not found"))
                        }
                    }
                } catch (e: Exception) {
                    if (localUser == null) {
                        emit(Resource.Error(e.message ?: "Network error"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun getAllUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())

        try {
            // Get from local first
            userDao.getAllUsers().collect { localUsers ->
                if (localUsers.isNotEmpty()) {
                    emit(Resource.Success(localUsers.map { it.toDomain() }))
                }

                // Then fetch from remote
                try {
                    val response = apiService.getAllUsers()
                    if (response.success && response.data != null) {
                        val users = response.data.map { it.toDomain() }
                        users.forEach { user ->
                            userDao.insertUser(user.toEntity())
                        }
                        emit(Resource.Success(users))
                    } else {
                        if (localUsers.isEmpty()) {
                            emit(Resource.Error(response.message ?: "Failed to fetch users"))
                        }
                    }
                } catch (e: Exception) {
                    if (localUsers.isEmpty()) {
                        emit(Resource.Error(e.message ?: "Network error"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun createUser(user: User): Resource<User> {
        return try {
            val response = apiService.createUser(user.toDto())
            if (response.success && response.data != null) {
                val createdUser = response.data.toDomain()
                userDao.insertUser(createdUser.toEntity())
                Resource.Success(createdUser)
            } else {
                Resource.Error(response.message ?: "Failed to create user")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun updateUser(user: User): Resource<User> {
        return try {
            val response = apiService.updateUser(user.id, user.toDto())
            if (response.success && response.data != null) {
                val updatedUser = response.data.toDomain()
                userDao.updateUser(updatedUser.toEntity())
                Resource.Success(updatedUser)
            } else {
                Resource.Error(response.message ?: "Failed to update user")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun deleteUser(userId: String): Resource<Unit> {
        return try {
            val response = apiService.deleteUser(userId)
            if (response.success) {
                userDao.deleteUserById(userId)
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message ?: "Failed to delete user")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun syncUsers(): Resource<Unit> {
        return try {
            val response = apiService.getAllUsers()
            if (response.success && response.data != null) {
                response.data.forEach { userDto ->
                    userDao.insertUser(userDto.toDomain().toEntity())
                }
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message ?: "Failed to sync users")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}