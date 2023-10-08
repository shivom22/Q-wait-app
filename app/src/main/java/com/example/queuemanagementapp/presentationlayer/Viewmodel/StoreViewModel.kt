package com.example.queuemanagementapp.presentationlayer.Viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queuemanagementapp.model.Coordinates
import com.example.queuemanagementapp.model.NearbyStoreItem
import com.example.queuemanagementapp.repository.StoreRepository
import com.example.queuemanagementapp.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel(){
    val  storeData : State<ApiState<List<NearbyStoreItem>?>?> = mutableStateOf(ApiState.Loading())

    fun nearbyStore(coordinates: Coordinates){
        viewModelScope.launch(Dispatchers.IO){
            storeRepository.nearbyStore(coordinates)
        }
    }
}