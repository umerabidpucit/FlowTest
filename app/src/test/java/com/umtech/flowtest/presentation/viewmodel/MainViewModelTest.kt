package com.umtech.flowtest.presentation.viewmodel

import com.umtech.flowtest.domain.usecase.FetchUsersWithPostCountUseCase
import com.umtech.flowtest.presentation.state.MainState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.Test

class MainViewModelTest {

    @Test
    fun `test initial state`() {
        val fetchUsersWithPostCountUseCase: FetchUsersWithPostCountUseCase = mockk()
        val viewModel = MainViewModel(fetchUsersWithPostCountUseCase)

        // Assert the initial state
        assertEquals(viewModel.state.value, MainState())
    }

    @Test
    fun `test loading state before data is fetched`() {
        val fetchUsersWithPostCountUseCase: FetchUsersWithPostCountUseCase = mockk()
        coEvery { fetchUsersWithPostCountUseCase() } returns flow { emit(emptyList()) }

        val viewModel = MainViewModel(fetchUsersWithPostCountUseCase)

        // Assert loading state
        assertTrue(viewModel.state.value.isLoading)
    }
}

