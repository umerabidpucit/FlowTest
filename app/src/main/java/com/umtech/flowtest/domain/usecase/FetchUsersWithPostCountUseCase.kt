package com.umtech.flowtest.domain.usecase

import com.umtech.flowtest.data.model.User
import com.umtech.flowtest.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class FetchUsersWithPostCountUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<List<User>> = repository.getUsersWithPostCount()
}