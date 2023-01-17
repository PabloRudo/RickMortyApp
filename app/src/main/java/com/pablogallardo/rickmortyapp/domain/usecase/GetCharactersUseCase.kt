package com.pablogallardo.rickmortyapp.domain.usecase

import com.pablogallardo.rickmortyapp.data.model.Character
import com.pablogallardo.rickmortyapp.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): List<Character> {
        return characterRepository.getCharacters(false)
    }
}