package com.pablogallardo.rickmortyapp.domain.usecase

import com.pablogallardo.rickmortyapp.domain.repository.CharacterRepository
import com.pablogallardo.rickmortyapp.helpers.extensions.resultOf
import javax.inject.Inject

class RefreshCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(page: Int = 1): Result<Unit> = resultOf {
        characterRepository.refreshCharacters(page)
    }
}