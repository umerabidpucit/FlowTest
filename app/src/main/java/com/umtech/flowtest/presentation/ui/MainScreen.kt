package com.umtech.flowtest.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.umtech.flowtest.presentation.state.MainIntent
import com.umtech.flowtest.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = getViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(MainIntent.LoadData)
    }

    when {
        state.isLoading -> CircularProgressIndicator()
        state.error != null -> Text("Error: ${state.error}")
        else -> LazyColumn {
            items(state.data) { item ->
                Text(item.name)
            }
        }
    }
}