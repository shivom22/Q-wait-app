package com.example.queuemanagementapp.presentationlayer.Viewmodel


import android.location.LocationRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queuemanagementapp.api.ApiService
import com.example.queuemanagementapp.model.Coordinates
import com.example.queuemanagementapp.model.NearbyStoreItem
import com.example.queuemanagementapp.repository.StoreRepository
import com.example.queuemanagementapp.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository,val apiService: ApiService
) : ViewModel(){
    private val _storeLiveData = MutableStateFlow<ApiState<List<NearbyStoreItem>>>(ApiState.Loading())
    val storeData: StateFlow<ApiState<List<NearbyStoreItem>>>
        get() = _storeLiveData
    fun sendLocation(){
        viewModelScope.launch(Dispatchers.IO){
            val location =  Coordinates(latti = 0, long = 0)
            val call = apiService.sendLocation(location)
            call.enqueue(object : Callback<List<NearbyStoreItem>> {
                override fun onResponse(call: Call<List<NearbyStoreItem>>, response: Response<List<NearbyStoreItem>>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                       viewModelScope.launch {
                           _storeLiveData.emit(ApiState.Success(responseBody))
                       }
                        Log.d("Testresp", responseBody?.toString() ?: "Response body is null")
                    } else {
                        // Handle unsuccessful response
                        viewModelScope.launch {
                            _storeLiveData.emit(ApiState.Error("response body is null"))
                        }
                        Log.e("Testresp", "Request failed with code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<NearbyStoreItem>>, t: Throwable) {
                    viewModelScope.launch{
                        _storeLiveData.emit(ApiState.Error(t.message))
                    }
                    Log.e("Testresp", "Request failed: ${t.message}")
                }
            })
        }
    }


}