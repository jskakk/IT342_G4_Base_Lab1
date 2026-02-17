package com.studyspace.mobile.api

data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String? = null
)

data class AuthRequest(
    val identifier: String,
    val password: String
)

data class AuthResponse(
    val id: Long,
    val username: String,
    val email: String
)
