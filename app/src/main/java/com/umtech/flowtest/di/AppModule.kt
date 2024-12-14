package com.umtech.flowtest.di

import com.umtech.flowtest.data.api.ApiService
import com.umtech.flowtest.data.remote.RemoteDataSource
import com.umtech.flowtest.data.repository.UserRepository
import com.umtech.flowtest.data.repository.UserRepositoryImpl
import com.umtech.flowtest.domain.usecase.FetchUserUseCase
import com.umtech.flowtest.domain.usecase.FetchUsersWithPostCountUseCase
import com.umtech.flowtest.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/SharminSirajudeen/test_resources/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    single { RemoteDataSource(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { FetchUsersWithPostCountUseCase(get()) }
    viewModel { MainViewModel(get()) }
}