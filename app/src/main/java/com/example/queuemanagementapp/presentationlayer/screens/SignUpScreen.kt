package com.example.queuemanagementapp.presentationlayer.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.abc.model.SignupRequest
import com.example.queuemanagementapp.presentationlayer.Viewmodel.AuthViewModel
import com.example.queuemanagementapp.presentationlayer.screens.Signup.Companion.password
import com.example.queuemanagementapp.presentationlayer.screens.Signup.Companion.username
import com.example.queuemanagementapp.R
import com.example.queuemanagementapp.ui.theme.Purple80
import com.example.queuemanagementapp.ui.theme.fontFamily
import com.example.queuemanagementapp.utils.ApiState
    class  Signup{
        companion object {
            lateinit var  username : MutableState<String>
            lateinit var password  : MutableState<String>
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpScreen(navController: NavController) {
        val lifecycle = LocalLifecycleOwner.current
        var isLoaderVisible by rememberSaveable { mutableStateOf(false) }
        var errortxt by rememberSaveable { mutableStateOf("") }
        var isError by rememberSaveable { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        username = rememberSaveable { mutableStateOf("") }
        password  = rememberSaveable { mutableStateOf("") }
        var showPassword by rememberSaveable { mutableStateOf(value = false) }
        var reEnteredPassword by rememberSaveable { mutableStateOf(value = "") }
        var showReEnteredPassword by rememberSaveable { mutableStateOf(value = false) }
        val authViewModel = hiltViewModel<AuthViewModel>()
        val validateInfo = authViewModel.validateInfo(
            emailAddress = username,
            password = password,
            reEnteredPassword = reEnteredPassword
        )
        authViewModel.userLiveData.observe(lifecycle) {
            isLoaderVisible = false
            when (it) {
                is ApiState.Success -> {
                    navController.navigate("otp")
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
        ) {
            Row {
                Text(
                    text = "Welcome",
                    fontSize = 44.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 8.dp)
                )
                Text(
                    text = " !",
                    color = Color(0xFF41d3bd),
                    fontSize = 44.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 30.dp)
                )
            }
            Text(
                text = "Create a New Account",
                color = Color(0xFF9da8b4),
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 30.sp
                ),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
            Spacer(modifier = Modifier.heightIn(70.dp))
            OutlinedTextField(
                value = username.value,
                enabled = !isLoaderVisible,
                isError = validateInfo.second == "Please enter the Email!" && isError,
                onValueChange = { username.value = it },
                label = {
                    Text(
                        text = "Email ID",
                        style = TextStyle(
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
                modifier = Modifier
                    .fillMaxWidth(),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.new_color),
                    focusedLabelColor = colorResource(id = R.color.new_color),
                    cursorColor = Purple80,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {

                    }
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                enabled = !isLoaderVisible,
                isError = validateInfo.second == "Please enter the password!" && isError,
                shape = RoundedCornerShape(18.dp),
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
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.new_color),
                    focusedLabelColor = colorResource(id = R.color.new_color),
                    cursorColor = Purple80,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle Done button click */ }
                ),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        text = "Password",
                        style = TextStyle(
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
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = reEnteredPassword,
                onValueChange = { reEnteredPassword = it },
                shape = RoundedCornerShape(18.dp),
                enabled = !isLoaderVisible,
                isError = validateInfo.second == "Please confirm your password!" && isError,
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
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.new_color),
                    focusedLabelColor = colorResource(id = R.color.new_color),
                    cursorColor = Purple80,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle Done button click */ }
                ),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        text = "Confirm your Password",
                        style = TextStyle(
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )
                },
                trailingIcon = {
                    if (showReEnteredPassword) {
                        IconButton(onClick = { showReEnteredPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showReEnteredPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.heightIn(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isError) {
                    Image(
                        modifier = Modifier.padding(start = 4.dp),
                        painter = painterResource(id = R.drawable._055461_bxs_error_icon),
                        contentDescription = null
                    )
                    Text(
                        text = errortxt, color = Color.Red, style = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                enabled = !isLoaderVisible,
                contentPadding = PaddingValues(vertical = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.new_color),
                                Purple80
                            )
                        ),
                        RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    keyboardController?.hide()
                    isError = false
                    if (validateInfo.first) {
                        authViewModel.signupUser(SignupRequest(username.value))
                        navController.navigate("otp")
                    } else {
                        isError = true
                        errortxt = validateInfo.second
                    }

                }) {
                Text(
                    text = "SIGN UP",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                )
            }
            TextButton(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Already have an account?",
                    style = TextStyle(
                        fontFamily = fontFamily
                    ),
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = "Log In",
                    style = TextStyle(
                        fontFamily = fontFamily,
                        brush = Brush.horizontalGradient(
                            listOf(colorResource(id = R.color.new_color), Purple80)
                        )
                    ),
                    fontSize = 18.sp
                )
            }

        }
    }




