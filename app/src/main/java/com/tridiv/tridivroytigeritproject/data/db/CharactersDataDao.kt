package com.tridiv.tridivroytigerit.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tridivtigritproject.data.model.CharacterDaoItem

@Dao
interface CharactersDataDao {

    @Insert
    fun saveCharactersInDB(item: CharacterDaoItem)

    @Query("select * from CharacterDaoItem")
    fun getAllTelevisionData(): LiveData<List<CharacterDaoItem>>

    @Query("DELETE FROM CharacterDaoItem")
    fun clearTable()

}