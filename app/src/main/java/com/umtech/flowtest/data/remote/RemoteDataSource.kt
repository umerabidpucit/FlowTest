package com.umtech.flowtest.data.remote

import com.umtech.flowtest.data.model.Post
import com.umtech.flowtest.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource {
//    fun fetchData(): Flow<List<User>> = flow {
//        emit(
//            listOf(
//                User(1, "Item 1", "url", "thumbnail"),
//                User(2, "Item 1", "url", "thumbnail")
//            )
//        )
//    }

    fun fetchUsers(): Flow<List<User>> = flow { emit(apiService.fetchUsers()) }
    fun fetchPosts(): Flow<List<Post>> = flow { emit(apiService.fetchPosts()) }
}