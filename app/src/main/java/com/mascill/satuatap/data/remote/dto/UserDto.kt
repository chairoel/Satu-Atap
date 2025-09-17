package com.mascill.satuatap.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "profile_image_url")
    val profileImageUrl: String,
    @Json(name = "created_at")
    val createdAt: Long,
    @Json(name = "updated_at")
    val updatedAt: Long
)