package com.tridiv.tridivroytigeritproject.data.repository

import androidx.lifecycle.LiveData
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharaterDetailsResp.CharacterDetailsRespBody

interface Repository {

    suspend fun getCharactersList(): CharactersRespBody?
    suspend fun getCharacterDetails(id: Int): CharacterDetailsRespBody?

    fun getAllCharactersData(): LiveData<List<CharacterDaoItem>>

    fun insertCharacterDataInDb(item: CharacterDaoItem)

    fun clearDbNew()
}