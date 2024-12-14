package com.umtech.flowtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umtech.flowtest.domain.usecase.FetchUsersWithPostCountUseCase
import com.umtech.flowtest.presentation.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

open class MainViewModel(private val fetchUsersWithPostCountUseCase: FetchUsersWithPostCountUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(MainState())
    open val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        loadUsersWithPostCount()
    }

    private fun loadUsersWithPostCount() {
        viewModelScope.launch {
            fetchUsersWithPostCountUseCase()
                .onStart { _state.value = _state.value.copy(isLoading = true) }
                .catch { error ->
                    _state.value = _state.value.copy(isLoading = false, error = error.message)
                }
                .collect { users ->
                    _state.value = MainState(data = users, isLoading = false)
                }
        }
    }
}