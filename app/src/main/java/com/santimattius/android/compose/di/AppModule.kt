package com.santimattius.android.compose.di

import com.santimattius.android.compose.core.network.ktorHttpClient
import com.santimattius.android.compose.feature.main.data.MovieRepository
import com.santimattius.android.compose.feature.main.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single(qualifier = named(name = "http_client")) { ktorHttpClient(apiKey = "asdas") }
    single { MovieRepository(client = get(qualifier = named(name = "http_client"))) }
    viewModel { MainViewModel(repository = get()) }
}