package com.pablogallardo.rickmortyapp.domain.repository

import com.pablogallardo.rickmortyapp.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun observeCharacters(): Flow<List<Character>>

    suspend fun getCharacters(refresh: Boolean = true): List<Character>

    suspend fun getCharacter(id: Int): Character

    suspend fun refreshCharacters(page: Int = 1)

    suspend fun saveCharacters(characters: List<Character>)
}