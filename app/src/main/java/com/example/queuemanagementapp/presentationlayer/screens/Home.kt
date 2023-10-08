package com.example.queuemanagementapp.presentationlayer.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.queuemanagementapp.presentationlayer.Viewmodel.StoreViewModel
import com.example.queuemanagementapp.model.NearbyStoreItem
import com.example.queuemanagementapp.utils.ApiState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: StoreViewModel = hiltViewModel()


    when (viewModel.storeData.value) {
        is ApiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        is ApiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                "Some Error has occurred\nPlease try again!",
                Toast.LENGTH_LONG
            ).show()
            Log.d(
                "retro",
                (viewModel.storeData.value as ApiState.Error<List<NearbyStoreItem>?>).errorMsg!!
            )
        }

        is ApiState.Success -> {
            Surface(
                color = Color.Transparent
            ) {
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            containerColor = Color.White,
                        ) {

                        }
                    }
                ) { _ ->


                }
            }
        }
        else -> {

        }
    }
}





