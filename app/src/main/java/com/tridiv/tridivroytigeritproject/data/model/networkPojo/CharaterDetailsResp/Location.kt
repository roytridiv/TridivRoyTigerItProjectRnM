package com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharaterDetailsResp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name")
    val name: String?,
    @Json(name = "url")
    val url: String?
)