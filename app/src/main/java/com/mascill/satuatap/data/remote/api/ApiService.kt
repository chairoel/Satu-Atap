package com.mascill.satuatap.data.remote.api

import com.mascill.satuatap.data.remote.dto.UserDto
import com.mascill.satuatap.data.remote.dto.ApiResponse
import retrofit2.http.*

interface ApiService {

    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): ApiResponse<UserDto>

    @GET("users")
    suspend fun getAllUsers(): ApiResponse<List<UserDto>>

    @POST("users")
    suspend fun createUser(@Body userDto: UserDto): ApiResponse<UserDto>

    @PUT("users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body userDto: UserDto
    ): ApiResponse<UserDto>

    @DELETE("users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): ApiResponse<Unit>
}