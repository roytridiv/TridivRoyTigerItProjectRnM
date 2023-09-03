package com.example.tridivtigritproject.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDetailsReqBody(
    @Json(name = "id")
    val id: Int?
)
