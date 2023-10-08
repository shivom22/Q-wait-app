package com.example.queuemanagementapp.utils

import okhttp3.ResponseBody

sealed class ApiState<T>(
    val data: T? = null,
    val errorMsg: String? = null
) {
    class Success<T>(data: T? =null) : ApiState<T>(data = data)

    class Error<T>(errorMsg: String?,data: T? = null) : ApiState<T>(errorMsg =errorMsg, data = data)

    class Loading<T> : ApiState<T>()
}