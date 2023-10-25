package com.example.queuemanagementapp.presentationlayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.queuemanagementapp.presentationlayer.screens.HomeScreen
import com.example.queuemanagementapp.presentationlayer.screens.LoginScreen
import com.example.queuemanagementapp.presentationlayer.screens.OtpScreen
import com.example.queuemanagementapp.presentationlayer.screens.SignUpScreen


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable( "signup"){ SignUpScreen(navController) }
        composable( "home"){ HomeScreen(navController) }
        composable("otp"){ OtpScreen( navController)}
    }
}