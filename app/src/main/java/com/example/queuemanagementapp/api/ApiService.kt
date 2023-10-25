package com.example.queuemanagementapp.api

import android.location.LocationRequest
import com.example.abc.model.SignupRequest
import com.example.abc.model.UserRequest
import com.example.abc.model.UserResponse
import com.example.abc.model.otpRequest
import com.example.queuemanagementapp.model.Coordinates
import com.example.queuemanagementapp.model.NearbyStoreItem
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface  ApiService {
     @Headers("accept: application/json, text/plain, */*")
     @POST("store/nearby")
     fun sendLocation(@Body location: Coordinates): Call<List<NearbyStoreItem>>
     @POST("/auth/login")
     suspend  fun logInUser(@Body userRequest: UserRequest): Response<UserResponse>

     @POST("/auth/signup")
     fun signup(@Body signupRequest: SignupRequest): Call<ResponseBody>

     @POST("/auth/otpVerification")
     fun  getotpsignup(@Body otpRequest: otpRequest):Call<ResponseBody>

     @Headers("accept: application/json, text/plain, */*")
     @POST("/store/nearby")
     fun nearbyStore(@Body  coordinates: Coordinates):Response<List<NearbyStoreItem>>
 }


