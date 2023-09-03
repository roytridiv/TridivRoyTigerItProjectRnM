package com.tridiv.tridivroytigerit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tridivtigritproject.data.model.CharacterDaoItem

@Database(entities = [CharacterDaoItem::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDataDao
}