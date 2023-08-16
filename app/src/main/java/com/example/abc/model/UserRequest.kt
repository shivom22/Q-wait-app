package com.example.abc.model

data class UserRequest(
    val email: String,
    val isStore: Boolean,
    val password: String
)