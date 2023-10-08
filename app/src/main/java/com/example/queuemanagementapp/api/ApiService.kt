package com.example.queuemanagementapp.api

import com.example.abc.model.SignupRequest
import com.example.abc.model.UserRequest
import com.example.abc.model.UserResponse
import com.example.abc.model.otpRequest
import com.example.queuemanagementapp.model.Coordinates
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface  ApiService {
     @POST("/auth/login")
     suspend  fun logInUser(@Body userRequest: UserRequest): Response<UserResponse>

     @POST("/auth/signup")
     fun signup(@Body signupRequest: SignupRequest): Call<ResponseBody>

     @POST("/auth/otpVerification")
     fun  getotpsignup(@Body otpRequest: otpRequest):Call<ResponseBody>

     @POST("/store/nearby")
     fun nearbyStore(@Body  coordinates: Coordinates):Call<ResponseBody>
 }

