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

class FetchUserUseCaseTest {

    private lateinit var fetchUserUseCase: FetchUserUseCase
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        // Mocking the UserRepository
        userRepository = mockk()

        // Initialize the use case with the mocked repository
        fetchUserUseCase = FetchUserUseCase(userRepository)
    }

    @Test
    fun `invoke should return a list of users correctly`() = runTest {
        // Given
        val mockUsers = listOf(
            User(1, "User 1", "url1", "thumbnail1"),
            User(2, "User 2", "url2", "thumbnail2")
        )

        // Mocking the repository's behavior
        coEvery { userRepository.getUserData() } returns kotlinx.coroutines.flow.flow {
            emit(mockUsers)
        }

        // When
        val result = fetchUserUseCase.invoke().first()

        // Then
        assertEquals(2, result.size)  // Ensure there are 2 users in the list
        assertEquals("User 1", result[0].name)  // Verify User 1's name
        assertEquals("User 2", result[1].name)  // Verify User 2's name
    }

    @Test
    fun `invoke should return an empty list if repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<User>()

        // Mocking the repository's behavior
        coEvery { userRepository.getUserData() } returns kotlinx.coroutines.flow.flow {
            emit(emptyList)
        }

        // When
        val result = fetchUserUseCase.invoke().first()

        // Then
        assertEquals(0, result.size)  // Ensure the list is empty
    }

    @Test
    fun `invoke should return correct data when repository returns users`() = runTest {
        // Given
        val mockUsers = listOf(
            User(1, "User 1", "url1", "thumbnail1"),
            User(2, "User 2", "url2", "thumbnail2")
        )

        // Mock the repository's behavior
        coEvery { userRepository.getUserData() } returns kotlinx.coroutines.flow.flow {
            emit(mockUsers)
        }

        // When
        val result = fetchUserUseCase.invoke().first()

        // Then
        assertEquals(2, result.size)  // Ensure there are 2 users
        assertEquals("User 1", result[0].name)  // Verify User 1's name
        assertEquals("User 2", result[1].name)  // Verify User 2's name
    }
}
