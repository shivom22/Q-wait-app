package com.example.abc.model

import androidx.compose.runtime.MutableState

data class UserRequest(
    val email: String,
    val isStore: Boolean,
    val password: String
)