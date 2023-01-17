package com.pablogallardo.rickmortyapp.data.source.remote

import com.pablogallardo.rickmortyapp.data.model.Character
import com.pablogallardo.rickmortyapp.data.model.toCharacter
import com.pablogallardo.rickmortyapp.data.source.apiHandler
import com.pablogallardo.rickmortyapp.data.source.remote.api.CharacterApi
import com.pablogallardo.rickmortyapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterApi: CharacterApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getCharacters(page: Int = 1): List<Character> = withContext(dispatcher) {
        val response = apiHandler { characterApi.getCharacters(page) }

        response
            .results
            .map {
                it.toCharacter()
            }
    }
}