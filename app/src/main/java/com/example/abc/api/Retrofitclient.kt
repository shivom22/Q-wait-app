package com.example.abc.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofitclient {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun init(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

}