package com.umtech.flowtest.data.repository

import com.umtech.flowtest.data.model.User
import kotlinx.coroutines.flow.Flow

interface  UserRepository {
    fun getUserData(): Flow<List<User>>
}