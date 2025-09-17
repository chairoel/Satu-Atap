package com.mascill.satuatap.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "message")
    val message: String?,
    @Json(name = "data")
    val data: T?
)