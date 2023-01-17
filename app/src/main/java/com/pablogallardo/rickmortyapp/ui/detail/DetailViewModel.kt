package com.pablogallardo.rickmortyapp.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogallardo.rickmortyapp.data.Empty
import com.pablogallardo.rickmortyapp.data.ResultError
import com.pablogallardo.rickmortyapp.data.model.Character
import com.pablogallardo.rickmortyapp.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val character: Character = Character(),
    val isLoading: Boolean = false,
    val error: ResultError = Empty
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    val uiState by lazy {
        MutableStateFlow(DetailUiState(isLoading = true))
    }

    fun initData(data: Bundle) {
        val id = data.getInt(DetailActivity.INTENT_ID, 0)
        getCharacterById(id)
    }

    private fun getCharacterById(id: Int) {
        viewModelScope.launch {

            getCharacterUseCase(id)
                .onSuccess { list ->
                    setSuccessState(list)
                }
                .onFailure { error ->
                    setErrorState(error as ResultError)
                }
        }
    }

    private fun setSuccessState(character: Character) {
        uiState.update {
            it.copy(
                character = character,
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
}