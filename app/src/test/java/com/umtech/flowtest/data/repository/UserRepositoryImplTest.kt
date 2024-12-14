package com.umtech.flowtest.data.repository

import com.umtech.flowtest.data.model.Post
import com.umtech.flowtest.data.model.User
import com.umtech.flowtest.data.remote.RemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        // Initialize mockk for RemoteDataSource
        remoteDataSource = mockk()
        userRepository = UserRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `getUserData should return list of users from remoteDataSource`() = runTest {
        // Given
        val mockUsers = listOf(
            User(1, "User 1", "url1", "thumbnail1"),
            User(2, "User 2", "url2", "thumbnail2")
        )
        coEvery { remoteDataSource.fetchData() } returns flow { emit(mockUsers) }

        // When
        val result = userRepository.getUserData().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("User 1", result[0].name)
        assertEquals("User 2", result[1].name)
    }

    @Test
    fun `getUsersWithPostCount should return users with post count and posts`() = runTest {
        // Given
        val mockUsers = listOf(
            User(1, "User 1", "url1", "thumbnail1"),
            User(2, "User 2", "url2", "thumbnail2")
        )

        val mockPosts = listOf(
            Post(1, 1, "Post 1", "Content 1"), // User 1 post
            Post(2, 1, "Post 2", "Content 2"), // User 1 post
            Post(3, 2, "Post 3", "Content 3")  // User 2 post
        )

        coEvery { remoteDataSource.fetchUsers() } returns flow { emit(mockUsers) }
        coEvery { remoteDataSource.fetchPosts() } returns flow { emit(mockPosts) }

        // When
        val result = userRepository.getUsersWithPostCount().first()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].postCount) // User 1 has 2 posts
        assertEquals(1, result[1].postCount) // User 2 has 1 post
        assertEquals("Post 1", result[0].posts?.get(0)?.title) // First post for User 1
        assertEquals("Post 2", result[1].posts?.get(0)?.title) // First post for User 2
    }

    @Test
    fun `getUsersWithPostCount should return empty posts if no matching posts for user`() =
        runTest {
            // Given
            val mockUsers = listOf(
                User(1, "User 1", "url1", "thumbnail1"),
                User(2, "User 2", "url2", "thumbnail2")
            )

            val mockPosts = listOf(
                Post(1, 1, "Post 1", "Content 1") // Only one post for User 1
            )

            coEvery { remoteDataSource.fetchUsers() } returns flow { emit(mockUsers) }
            coEvery { remoteDataSource.fetchPosts() } returns flow { emit(mockPosts) }

            // When
            val result = userRepository.getUsersWithPostCount().first()

            // Then
            assertEquals(2, result.size)
            assertEquals(1, result[0].postCount) // User 1 has 1 post
            assertEquals(0, result[1].postCount) // User 2 has no posts
            result[1].posts?.isEmpty()?.let { assertTrue(it) } // User 2 has no posts
        }

    @Test
    fun `getUsersWithPostCount should return empty list when no users and posts are available`() =
        runTest {
            // Given
            val mockUsers = emptyList<User>()
            val mockPosts = emptyList<Post>()

            coEvery { remoteDataSource.fetchUsers() } returns flow { emit(mockUsers) }
            coEvery { remoteDataSource.fetchPosts() } returns flow { emit(mockPosts) }

            // When
            val result = userRepository.getUsersWithPostCount().first()

            // Then
            assertTrue(result.isEmpty()) // No users available, so the list should be empty
        }
}