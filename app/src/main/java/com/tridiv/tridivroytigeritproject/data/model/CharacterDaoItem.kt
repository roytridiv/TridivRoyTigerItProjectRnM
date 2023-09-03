package com.example.tridivtigritproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterDaoItem(
    @ColumnInfo(name = "character_id") val characterId: Int,
    @ColumnInfo(name = "name")val name:  String,
    @ColumnInfo(name ="status")val status: String,
    @ColumnInfo(name ="species") val species: String,
    @ColumnInfo(name ="type") val type :String,
    @ColumnInfo(name ="gender") val gender:String,
    @ColumnInfo(name ="origin") val origin : String,
    @ColumnInfo(name ="location") val location : String,
    @ColumnInfo(name ="image") val image: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
