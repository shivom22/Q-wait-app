package com.example.queuemanagementapp.repository


import com.example.queuemanagementapp.api.ApiService
import com.example.queuemanagementapp.model.NearbyStoreItem
import com.example.queuemanagementapp.utils.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class StoreRepository @Inject constructor(private val apiService: ApiService) {
    private val _storeLiveData = MutableStateFlow<ApiState<List<NearbyStoreItem>>>(ApiState.Loading())
    val sateLiveData : StateFlow<ApiState<List<NearbyStoreItem>>>
        get() = _storeLiveData
    }



