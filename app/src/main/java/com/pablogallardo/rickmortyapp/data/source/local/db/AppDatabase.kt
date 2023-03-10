package com.pablogallardo.rickmortyapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pablogallardo.rickmortyapp.data.model.CharacterDbModel

@Database(entities = [CharacterDbModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}