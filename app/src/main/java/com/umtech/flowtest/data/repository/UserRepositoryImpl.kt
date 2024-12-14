package com.umtech.flowtest.data.repository

import com.umtech.flowtest.data.model.User
import com.umtech.flowtest.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val dataSource: RemoteDataSource) : UserRepository {
    override fun getUserData(): Flow<List<User>> {
        return dataSource.fetchData()
    }
}