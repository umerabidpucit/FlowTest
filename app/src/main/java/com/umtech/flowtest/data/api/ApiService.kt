package com.umtech.flowtest.data.api

import com.umtech.flowtest.data.model.Post
import com.umtech.flowtest.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun fetchUsers(): List<User>

    @GET("posts")
    suspend fun fetchPosts(): List<Post>

}