package com.umtech.flowtest.data.remote

import com.umtech.flowtest.data.api.ApiService
import com.umtech.flowtest.data.model.Post
import com.umtech.flowtest.data.model.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RemoteDataSourceTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `fetchData should return hardcoded list of users`() = runTest {
        // When
        val result = remoteDataSource.fetchData().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Item 1", result[0].name)
        assertEquals("Item 1", result[1].name)
    }

    @Test
    fun `fetchUsers should call apiService and return a list of users`() = runTest {
        // Given
        val mockUsers = listOf(User(1, "User 1", "url1", "thumbnail1"), User(2, "User 2", "url2", "thumbnail2"))
        Mockito.`when`(apiService.fetchUsers()).thenReturn(mockUsers)

        // When
        val result = remoteDataSource.fetchUsers().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("User 1", result[0].name)
        assertEquals("User 2", result[1].name)
    }

    @Test
    fun `fetchPosts should call apiService and return a list of posts`() = runTest {
        // Given
        val mockPosts = listOf(Post(1, 2, "Post 1", "Body 1"), Post(2, 5,"Post 2", "Content 2"))
        Mockito.`when`(apiService.fetchPosts()).thenReturn(mockPosts)

        // When
        val result = remoteDataSource.fetchPosts().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Post 1", result[0].title)
        assertEquals("Post 2", result[1].title)
    }

    @Test
    fun `fetchUsers should return empty list when apiService returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<User>()
        Mockito.`when`(apiService.fetchUsers()).thenReturn(emptyList)

        // When
        val result = remoteDataSource.fetchUsers().first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `fetchPosts should return empty list when apiService returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<Post>()
        Mockito.`when`(apiService.fetchPosts()).thenReturn(emptyList)

        // When
        val result = remoteDataSource.fetchPosts().first()

        // Then
        assertTrue(result.isEmpty())
    }
}