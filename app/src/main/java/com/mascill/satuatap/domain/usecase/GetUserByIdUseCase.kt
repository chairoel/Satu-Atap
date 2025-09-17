package com.mascill.satuatap.domain.usecase

import com.mascill.satuatap.data.model.User
import com.mascill.satuatap.data.repository.UserRepository
import com.mascill.satuatap.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Resource<User>> {
        return userRepository.getUserById(userId)
    }
}