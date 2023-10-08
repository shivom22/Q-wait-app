package com.example.queuemanagementapp.presentationlayer.Viewmodel

import android.text.TextUtils
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abc.model.SignupRequest
import com.example.abc.model.UserRequest
import com.example.abc.model.UserResponse
import com.example.abc.model.otpRequest
import com.example.queuemanagementapp.repository.Repository
import com.example.queuemanagementapp.utils.ApiState
import com.example.queuemanagementapp.utils.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: Repository
) : ViewModel() {
    val userLiveData: LiveData<ApiState<UserResponse>>
        get() = userRepository.userLiveData

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginUser(userRequest)
        }
    }

    fun signupUser(signupRequest: SignupRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.signupUser(signupRequest)
        }
    }
    fun otp(otpRequest: otpRequest){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.otp(otpRequest)
        }
    }

    fun validateCredentials(emailAddress: String, password: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(emailAddress)) {
            result = Pair(false, "Please provide the Email!")
        } else if (TextUtils.isEmpty(password)) {
            result = Pair(false, "Please enter the password!")
        } else if (!Helper.isValidEmail(emailAddress)) {
            result = Pair(false, "Email is invalid")
        } else if (!TextUtils.isEmpty(password) && password.length <= 5) {
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

    fun validateInfo(emailAddress: MutableState<String>, password: MutableState<String>, reEnteredPassword: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        val checkedpassword = checkPassword(reEnteredPassword)
        if (TextUtils.isEmpty(emailAddress.toString())) {
            result = Pair(false, "Please enter the Email!")
        } else if (TextUtils.isEmpty(password.value)) {
            result = Pair(false, "Please enter the password!")
        } else if (TextUtils.isEmpty(reEnteredPassword)) {
            result = Pair(false, "Please confirm your password!")
        }
        else if (!Helper.isValidEmail(emailAddress.value)) {
            result = Pair(false, "Please enter valid email")
        }
        else if (!checkedpassword.first){
            result =  Pair(false,checkedpassword.second)
        }
        else if(password.value != reEnteredPassword){
            result = Pair(false, "Please enter the same password")
        }
        return result
    }
    fun checkPassword(password: String):Pair<Boolean,String>{
        var result = Pair(true,"")
        var flagLower = false
        var flagUpper = false
        var flagNumber = false
        for(i in password.indices){
            if(password[i] in 'a'..'z')
                flagLower = true
            if(password[i] in 'A'..'Z')
                flagUpper = true
            if(password[i] in '0'..'9')
                flagNumber = true
        }
        if(!(flagLower && flagUpper && flagNumber) || password.length < 5){
         result = Pair (false,"Minimum length of password should be 5 characters There should be atleast one uppercase, lowercase and a numeric digit")
        }
        return  result
    }
    fun valiadteotp(){

    }
}