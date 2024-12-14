package com.umtech.flowtest.presentation.state

import com.umtech.flowtest.data.model.User

data class MainState(
    val data: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)