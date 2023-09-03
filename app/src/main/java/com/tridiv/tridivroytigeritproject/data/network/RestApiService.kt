package com.tridiv.tridivroytigeritproject.data.network


import com.example.tridivtigritproject.data.model.CharacterDetailsReqBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharaterDetailsResp.CharacterDetailsRespBody
import retrofit2.Response
import retrofit2.http.*

interface RestApiService {

    @GET("character")
    suspend fun getCharactersList(): Response<CharactersRespBody>


    @GET("character/by_id")
    suspend fun getCharacterDetails(
        @Body body: CharacterDetailsReqBody
    ): Response<CharacterDetailsRespBody>
}