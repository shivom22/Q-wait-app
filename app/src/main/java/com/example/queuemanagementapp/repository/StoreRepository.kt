package com.example.queuemanagementapp.repository

import com.example.queuemanagementapp.api.ApiService
import com.example.queuemanagementapp.model.Coordinates
import com.example.queuemanagementapp.model.NearbyStoreItem
import com.example.queuemanagementapp.utils.ApiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class StoreRepository @Inject constructor(private val apiService: ApiService) {
    private val _storeLiveData = MutableStateFlow<ApiState<List<NearbyStoreItem>?>?>(null)
    val  storeLiveData :    StateFlow<ApiState<List<NearbyStoreItem>?>?>
        get() = _storeLiveData

   suspend fun nearbyStore(coordinates: Coordinates){
       _storeLiveData.emit(ApiState.Loading())
       val response = apiService.nearbyStore(coordinates)
       handleResponse(response)

   }
   private suspend fun handleResponse(response: Call<ResponseBody>) {
        val gson  = Gson()
        try {
            val responseBody = response.execute()
            if (responseBody.isSuccessful && responseBody.errorBody() != null) {
                val userResponseJson = responseBody.body()?.string()
                if (!userResponseJson.isNullOrBlank()) {
                    val userResponse = gson.fromJson(userResponseJson, NearbyStoreItem::class.java)
                    _storeLiveData.emit(( ApiState.Success(listOf( userResponse))))
                } else {
                    _storeLiveData.emit(ApiState.Error("Response body is empty"))
                }
            } else {
               _storeLiveData.emit(ApiState.Error("Failed !"))
            }
        } catch (e: Exception) {
         _storeLiveData.emit(ApiState.Error(e.message ?: "Unknown error"))
        }
    }
}