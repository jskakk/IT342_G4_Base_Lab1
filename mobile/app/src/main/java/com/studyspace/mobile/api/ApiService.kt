package com.studyspace.mobile.api

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("api/users/register")
    suspend fun register(@Body user: User): Response<AuthResponse>
    
    @POST("api/users/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>
    
    @GET("api/users")
    suspend fun getAllUsers(): Response<List<AuthResponse>>
}
