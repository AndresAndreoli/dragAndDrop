package com.santimattius.android.compose.feature.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.android.compose.feature.main.data.MovieRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState(isLoading = true))
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.update { it.copy(isLoading = false, isFailure = true) }
    }

    init {
        fetch()
    }

    private fun fetch() {
        _state.update {
            it.copy(
                isLoading = false, data = listOf(
                    listOf(
                        MovieUiModel(
                            title = "1",
                            id = "1"
                        ),
                        MovieUiModel(
                            title = "2",
                            id = "2"
                        ),
                        MovieUiModel(
                            title = "3",
                            id = "3"
                        ),
                    ),
                    listOf(
                        SerieUiModel(
                            title = "4",
                            id = "4"
                        ),
                        SerieUiModel(
                            title = "5",
                            id = "5"
                        ),
                        SerieUiModel(
                            title = "6",
                            id = "6"
                        ),
                    ),
                )
            )
        }
    }

    fun move(fromIndex: Int, toIndex: Int) {
        val movies = _state.value.data.toMutableList()
        movies.add(toIndex, movies.removeAt(fromIndex))
        _state.update { it.copy(data = movies) }
    }
}