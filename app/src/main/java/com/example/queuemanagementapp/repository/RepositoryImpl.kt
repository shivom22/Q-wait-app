package com.example.queuemanagementapp.repository



import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abc.model.SignupRequest
import com.example.abc.model.UserRequest
import com.example.abc.model.UserResponse
import com.example.abc.model.otpRequest
import com.example.queuemanagementapp.api.ApiService
import com.example.queuemanagementapp.utils.ApiState
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) {
    private val _userLiveData = MutableLiveData<ApiState<UserResponse>>()
    val userLiveData : LiveData<ApiState<UserResponse>>
        get() = _userLiveData
    suspend fun loginUser(userRequest: UserRequest) {
        _userLiveData.postValue(ApiState.Loading())
        val response =apiService.logInUser(userRequest)
        handleResponse(response)
    }
   fun  signupUser(signupRequest: SignupRequest){
        _userLiveData.postValue(ApiState.Loading())
        val response =apiService.signup(signupRequest)
        handleResponsesignup(response)
    }
  fun otp(otpRequest: otpRequest){
        _userLiveData.postValue(ApiState.Loading())
        val response = apiService.getotpsignup(otpRequest)
        handleResponsesignup(response)
    }
    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful &&  response.body() != null){
            _userLiveData.postValue(ApiState.Success(response.body()!!))
        }
        else if(response.errorBody() != null){
            val error =  JSONObject(response.errorBody()!!.charStream().readText())
            _userLiveData.postValue(ApiState.Error(error.getString("message")))
        }
        else{
            _userLiveData.postValue(ApiState.Error("Udta Teer"))
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun handleResponsesignup(response: Call<ResponseBody>) {
        val gson  = Gson()
        try {
            val time = OkHttpClient().connectTimeoutMillis
            val responseBody = response.execute()
            if (responseBody.isSuccessful && responseBody.errorBody() != null) {
                val userResponseJson = responseBody.body()?.string()
                if (!userResponseJson.isNullOrBlank()) {
                    val userResponse = gson.fromJson(userResponseJson, UserResponse::class.java)
                        _userLiveData.postValue(ApiState.Success(userResponse))
                } else {
                    _userLiveData.postValue(ApiState.Error("Response body is empty"))
                }
            }
            else {
                _userLiveData.postValue(ApiState.Error("Failed !"))
            }
        } catch (e: Exception) {
            _userLiveData.postValue(ApiState.Error(e.message ?: "Unknown error"))
        }
}
}