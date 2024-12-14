package com.umtech.flowtest.data.repository

import com.umtech.flowtest.data.model.User
import com.umtech.flowtest.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val dataSource: RemoteDataSource) : UserRepository {
    override fun getUserData(): Flow<List<User>> {
        return dataSource.fetchData()
    }

    override fun getUsersWithPostCount(): Flow<List<User>> = flow {
        val users = dataSource.fetchUsers().first()
        val posts = dataSource.fetchPosts().first()

        val userListWithPostCount = users.map { user ->
            val userPosts = posts.filter { it.userId == user.userId }
            user.copy(
                postCount = userPosts.size,
                posts = userPosts // Assuming User has a `posts` property
            )
        }

        emit(userListWithPostCount)
    }
}