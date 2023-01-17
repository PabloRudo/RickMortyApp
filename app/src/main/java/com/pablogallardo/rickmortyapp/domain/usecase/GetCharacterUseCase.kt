package com.pablogallardo.rickmortyapp.domain.usecase

import com.pablogallardo.rickmortyapp.domain.repository.CharacterRepository
import javax.inject.Inject
import com.pablogallardo.rickmortyapp.data.model.Character
import com.pablogallardo.rickmortyapp.helpers.extensions.resultOf

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int): Result<Character> {
        return resultOf {
            characterRepository.getCharacter(id)
        }
    }
}