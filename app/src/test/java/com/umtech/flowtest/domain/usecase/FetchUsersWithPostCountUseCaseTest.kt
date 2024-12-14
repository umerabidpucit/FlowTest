package com.umtech.flowtest.domain.usecase

import com.umtech.flowtest.data.model.User
import com.umtech.flowtest.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchUsersWithPostCountUseCaseTest {

    private lateinit var fetchUsersWithPostCountUseCase: FetchUsersWithPostCountUseCase
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        // Mocking the UserRepository
        userRepository = mockk()

        // Initialize the use case with the mocked repository
        fetchUsersWithPostCountUseCase = FetchUsersWithPostCountUseCase(userRepository)
    }

    @Test
    fun `invoke should return users with post count correctly`() = runTest {
        // Given
        val mockUsers = listOf(
            User(1, "User 1", "url1", "thumbnail1", postCount = 2),
            User(2, "User 2", "url2", "thumbnail2", postCount = 1)
        )

        // Mocking the repository's behavior
        coEvery { userRepository.getUsersWithPostCount() } returns kotlinx.coroutines.flow.flow {
            emit(mockUsers)
        }

        // When
        val result = fetchUsersWithPostCountUseCase.invoke().first()

        // Then
        assertEquals(2, result.size)  // Ensure there are 2 users
        assertEquals(2, result[0].postCount)  // Ensure User 1 has 2 posts
        assertEquals(1, result[1].postCount)  // Ensure User 2 has 1 post
        assertEquals("User 1", result[0].name)  // Verify User 1's name
        assertEquals("User 2", result[1].name)  // Verify User 2's name
    }

    @Test
    fun `invoke should return empty list if repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<User>()

        // Mocking the repository's behavior
        coEvery { userRepository.getUsersWithPostCount() } returns kotlinx.coroutines.flow.flow {
            emit(emptyList)
        }

        // When
        val result = fetchUsersWithPostCountUseCase.invoke().first()

        // Then
        assertEquals(0, result.size)  // Ensure the list is empty
    }

    @Test
    fun `invoke should return correct data when repository returns data with posts`() = runTest {
        // Given
        val mockUsers = listOf(
            User(1, "User 1", "url1", "thumbnail1", postCount = 3),
            User(2, "User 2", "url2", "thumbnail2", postCount = 1)
        )

        // Mock the repository's behavior
        coEvery { userRepository.getUsersWithPostCount() } returns kotlinx.coroutines.flow.flow {
            emit(mockUsers)
        }

        // When
        val result = fetchUsersWithPostCountUseCase.invoke().first()

        // Then
        assertEquals(3, result[0].postCount)  // Verify that User 1 has 3 posts
        assertEquals(1, result[1].postCount)  // Verify that User 2 has 1 post
    }
}
