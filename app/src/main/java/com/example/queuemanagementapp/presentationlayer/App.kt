package com.example.queuemanagementapp.presentationlayer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.queuemanagementapp.presentationlayer.screens.HomeScreen
import com.example.queuemanagementapp.presentationlayer.screens.LoginScreen
import com.example.queuemanagementapp.presentationlayer.screens.OtpScreen
import com.example.queuemanagementapp.presentationlayer.screens.SignUpScreen


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