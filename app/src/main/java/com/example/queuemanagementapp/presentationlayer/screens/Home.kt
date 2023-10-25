package com.example.queuemanagementapp.presentationlayer.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.queuemanagementapp.model.NearbyStoreItem
import com.example.queuemanagementapp.presentationlayer.Viewmodel.StoreViewModel
import com.example.queuemanagementapp.ui.theme.fontFamily
import com.example.queuemanagementapp.utils.ApiState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
 fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val viewModel: StoreViewModel = hiltViewModel()
    val stores = viewModel.storeData.collectAsState()
    var isLoaderVisible by rememberSaveable { mutableStateOf(false) }
    var storeSearchList by remember {mutableStateOf<List<NearbyStoreItem>>(emptyList())  }
    viewModel.sendLocation()
    LaunchedEffect(Unit) {
        lifecycle.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.storeData.collect {
                    isLoaderVisible = false
                   when (it) {
                        is ApiState.Loading -> {
                            Log.d("Chaman", "loading ho rhi h")
                          isLoaderVisible =  true
                        }

                        is ApiState.Success -> {
                            Log.d("Chutiya", "success ho rhi h")
                           isLoaderVisible = false
                            val data = stores.value.data
                            if (data != null) {
                                storeSearchList = data
                            }
                        }

                        is ApiState.Error -> {
                            Toast.makeText(
                                context,
                                "Some Error has occurred\nPlease try again!",
                                Toast.LENGTH_LONG
                            ).show()

                            Log.d("Bsdka", "error ho rhi h")
                           isLoaderVisible = false
                        }
                   }
                }
            }
        }
    }

    if (isLoaderVisible) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier.size(56.dp)
            )
        }
    } else {
        Surface(
            color = Color.White
        ) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        tonalElevation = BottomAppBarDefaults.ContainerElevation
                    ) {

                    }
                },
                containerColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) { _ ->
             Components()
            }
        }
    }
}


@Composable
private fun Components() {
    var txt by remember { mutableStateOf("") }
    val viewModel: StoreViewModel = hiltViewModel()
    val stores = viewModel.storeData.collectAsState()
    var storeSearchList by remember { mutableStateOf<List<NearbyStoreItem>>(emptyList()) }
    val size = stores.value.data?.size ?: 0
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = txt,
                shape = RoundedCornerShape(16.dp),
                onValueChange = { it ->
                    txt = it
                    storeSearchList = stores.value.data?.filter {
                        it.shop.name.contains(txt, true)
                    } ?: emptyList()
                },

                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                },
                placeholder = {
                    Text(
                        "Search Stores",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp)
                    )
                },
                trailingIcon = {
                    if (txt.isNotEmpty()) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                txt = "" // Clear the text
                            }
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                ),
                modifier = Modifier
                    .height(49.dp)
                    .shadow(
                        elevation = 12.dp,
                        spotColor = Color(0x47000000),
                        ambientColor = Color(0xFFFFFFFF)
                    )
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {

                }),
            )
        }
        StoreList(storeList = storeSearchList)
        Text(
            text = "Nearby stores",
            color = Color.Black,
            style = TextStyle(
                fontSize = 34.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(top = 30.dp, start = 4.dp)
        )
        Spacer(modifier = Modifier.heightIn(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            items(size) {
                val res = stores.value.data
                val data = res?.get(it)
                if (data != null) {
                    Spacer(modifier = Modifier.heightIn(4.dp))
                    StoreCard(
                        name = data.shop.name,
                        counter = data.shop.counter,
                        customers = data.shop.ShopCounter.sum(),
                        address = data.shop.Address,
                        modifier = Modifier.padding(top = 8.dp),
                        avgTime = data.shop.avgtime.toString()
                    )
                }
            }
        }
    }
}



@Composable
fun StoreList(storeList: List<NearbyStoreItem>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(storeList.size) { store ->
            val  data = storeList[store]
            StoreCard(
                    name = data.shop.name,
                    counter = data.shop.counter,
                    customers = data.shop.ShopCounter.sum(),
                    address = data.shop.Address,
                    avgTime = data.shop.avgtime.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            Spacer(modifier = Modifier.widthIn(8.dp))
        }
    }
}










