package com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersRespBody(
    @Json(name = "info")
    val info: Info?,
    @Json(name = "results")
    val results: List<Result?>?
)