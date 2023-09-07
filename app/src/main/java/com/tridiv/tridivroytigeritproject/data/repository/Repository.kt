package com.tridiv.tridivroytigeritproject.data.repository

import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigeritproject.data.domain.common.ResultData
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharaterDetailsResp.CharacterDetailsRespBody
import retrofit2.Response

interface Repository {

    //suspend fun getCharactersList(): CharactersRespBody?
    suspend fun getCharacterDetails(id: Int): CharacterDetailsRespBody?

    fun getAllCharactersDataFromDB(): List<CharacterDaoItem>

    fun getCharacterDetailsFromDB(characterId: Int): CharacterDaoItem

    fun insertCharacterDataInDb(item: CharacterDaoItem)

    fun clearDbNew()

    suspend fun getCharactersList(): ResultData<Response<CharactersRespBody>>
}