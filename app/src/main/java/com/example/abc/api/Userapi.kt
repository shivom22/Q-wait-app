package com.example.abc.api

import com.example.abc.model.SignupRequest
import com.example.abc.model.UserRequest
import com.example.abc.model.UserResponse
import com.example.abc.model.otpRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


const val Base_url = "https://q-wait.onrender.com"
interface UserApi {

    @FormUrlEncoded
    @POST("/auth/login")
    fun logInUser(
        @Field("email")email: String,
        @Field("password")password: String,
        @Field("isStore")isStore: Boolean
    ): Call<UserResponse>

    @POST("/auth/signup")
    fun signup(@Body Sginup:SignupRequest):Call<ResponseBody>

    @POST("/auth/otpVerification")
    fun  getotpsignup(@Body otpRequest: otpRequest):Call<ResponseBody>

}

