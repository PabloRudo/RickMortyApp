package com.pablogallardo.rickmortyapp.di

import com.pablogallardo.rickmortyapp.domain.repository.CharacterRepository
import com.pablogallardo.rickmortyapp.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsCharacterRepository(repositoryImpl: CharacterRepositoryImpl) : CharacterRepository
}