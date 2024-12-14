package com.umtech.flowtest.di

import com.umtech.flowtest.data.remote.RemoteDataSource
import com.umtech.flowtest.data.repository.UserRepository
import com.umtech.flowtest.data.repository.UserRepositoryImpl
import com.umtech.flowtest.domain.usecase.FetchUserUseCase
import com.umtech.flowtest.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Data Layer
    single { RemoteDataSource() }
    single<UserRepository> { UserRepositoryImpl(get()) }

    // Domain Layer
    single { FetchUserUseCase(get()) }

    // ViewModel
    viewModel { MainViewModel(get()) }
}