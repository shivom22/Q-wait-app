package com.example.queuemanagementapp.presentationlayer.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.abc.model.UserRequest
import com.example.queuemanagementapp.R
import com.example.queuemanagementapp.presentationlayer.Viewmodel.AuthViewModel
import com.example.queuemanagementapp.ui.theme.Purple80
import com.example.queuemanagementapp.ui.theme.fontFamily
import com.example.queuemanagementapp.utils.ApiState


@Composable
fun LoginScreen(navController: NavController) {
    val lifecycle = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    var isLoaderVisible by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var errortxt by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(value = false) }
    var isStore by rememberSaveable { mutableStateOf(false) }
    val authViewModel = hiltViewModel<AuthViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current
    val validateresults =  authViewModel.validateCredentials(username,password)

    authViewModel.userLiveData.observe(lifecycle) {
        isLoaderVisible = false
        when (it) {
            is ApiState.Success -> {
                navController.navigate("home")
            }

            is ApiState.Error -> {
                isError = true
                errortxt = it.errorMsg.toString()
            }

            is ApiState.Loading -> {
                isLoaderVisible = true
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.welcome_back),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 30.dp, start = 8.dp)
            )
            Text(
                text = " !",
                color = Color(0xFF41d3bd),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp)
            )
        }
        Text(
            text = "Sign in to continue", color = Color(0xFF9da8b4), style = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
            ), modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    isStore = true
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ), modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Customer",
                    color = if (!isStore) Color.Black else Color(0xFF41d3bd),
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    )

                )
            }
            Button(
                onClick = {
                    isStore = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ), modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Store",
                    color = if (!isStore) Color(0xFF41d3bd) else Color.Black,
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    )
                )
            }
        }
        OutlinedTextField(
            value = username,
            enabled = !isLoaderVisible,
            onValueChange = { username = it },
            isError= validateresults.second == "Please provide the Email!" && isError,
            label = {
                Text(
                    text = "Email ID", textAlign = TextAlign.Center, style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_email_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Purple80,
                focusedBorderColor = colorResource(id = R.color.new_color),
                focusedLabelColor = colorResource(id = R.color.new_color),
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = password,
            enabled = !isLoaderVisible,
            onValueChange = { password = it },
            shape = RoundedCornerShape(18.dp),
            isError = validateresults.second == "Please enter the password!" && isError,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            modifier = Modifier
                .fillMaxWidth()

                .background(color = Color.Transparent, RoundedCornerShape(10.dp)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Purple80,
                focusedBorderColor = colorResource(id = R.color.new_color),
                focusedLabelColor = colorResource(id = R.color.new_color),
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(
                    text = "Password", style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                )
            },
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            })
        Row(
           modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isError) {
                Image(
                    modifier = Modifier.padding(  start = 4.dp),
                    painter = painterResource(id = R.drawable._055461_bxs_error_icon),
                    contentDescription = null
                )
                Text(
                    text = errortxt, color = Color.Red, style = TextStyle(
                        fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(  start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Box {
                    Text(
                        text = "Forgot Password?",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontFamily = fontFamily
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(contentPadding = PaddingValues(vertical = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.new_color), Purple80
                        )
                    ), RoundedCornerShape(8.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            enabled = !isLoaderVisible,
            onClick = {
                keyboardController?.hide()
                isError = false
                if (validateresults.first){
                    authViewModel.loginUser(UserRequest(username, isStore, password))
                }
                else{
                    isError = true
                    errortxt = validateresults.second
                }

            }) {
            Text(
                text = "LOGIN", color = Color.White, style = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            if (isLoaderVisible) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp), color = Color.Black, strokeWidth = 4.dp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Don't have an account?", color = Color.Black, style = TextStyle(
                    fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp
                )
            )
            TextButton(
                onClick = {
                    navController.navigate("signup")
                },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Sign Up", style = TextStyle(
                        brush = Brush.horizontalGradient(
                            listOf(colorResource(id = R.color.new_color), Purple80)
                        ), fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp
                    ), fontSize = 18.sp
                )
            }
        }
    }
}





