package com.umtech.flowtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umtech.flowtest.domain.usecase.FetchUserUseCase
import com.umtech.flowtest.presentation.state.MainIntent
import com.umtech.flowtest.presentation.state.MainState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val fetchDataUseCase: FetchUserUseCase) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val _intent = MutableSharedFlow<MainIntent>()
    val intent: SharedFlow<MainIntent> = _intent

    init {
        processIntents()
    }

    fun sendIntent(intent: MainIntent) {
        viewModelScope.launch { _intent.emit(intent) }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is MainIntent.LoadData -> loadData()
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            fetchDataUseCase()
                .onStart { _state.value = _state.value.copy(isLoading = true) }
                .catch { error ->
                    _state.value = _state.value.copy(isLoading = false, error = error.message)
                }
                .collect { data ->
                    _state.value = MainState(data = data, isLoading = false)
                }
        }
    }
}