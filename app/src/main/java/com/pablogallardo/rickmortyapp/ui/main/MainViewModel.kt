package com.pablogallardo.rickmortyapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogallardo.rickmortyapp.data.*
import com.pablogallardo.rickmortyapp.data.model.Character
import com.pablogallardo.rickmortyapp.domain.usecase.GetCharactersUseCase
import com.pablogallardo.rickmortyapp.domain.usecase.ObserveCharactersUseCase
import com.pablogallardo.rickmortyapp.domain.usecase.RefreshCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharactersUiState(
    val items: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: ResultError = Empty
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeCharactersUseCase: ObserveCharactersUseCase,
    private val refreshCharactersUseCase: RefreshCharactersUseCase,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(CharactersUiState(isLoading = true))

    init {
        viewModelScope.launch {
            observeCharactersUseCase().collect { result ->
                result
                    .onSuccess { list ->
                        setSuccessState(list)
                    }
                    .onFailure { error ->
                        setErrorState(error as ResultError)
                    }
            }
        }
    }

    fun loadCharacters(isNetworkAvailable: Boolean) = viewModelScope.launch {
        setLoadingState(true)

        if(isNetworkAvailable) {
            refreshCharactersUseCase()
                .onFailure {
                    setErrorState(it as ResultError)
                }
        } else {
            setErrorState(NoInternetError)
        }

        setLoadingState(false)
    }

    private fun setSuccessState(list: List<Character>) {
        uiState.update {
            it.copy(
                items = list,
                isLoading = false,
                error = Empty
            )
        }
    }

    private fun setErrorState(error: ResultError) {
        uiState.update {
            it.copy(
                error = error,
                isLoading = false
            )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    fun notifyLastVisible() = viewModelScope.launch {
        val size = getCharactersUseCase().size

        val page = (size / 20) + 1
        refreshCharactersUseCase(page)
    }
}
