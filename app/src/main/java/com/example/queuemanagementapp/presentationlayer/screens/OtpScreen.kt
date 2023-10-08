package com.example.queuemanagementapp.presentationlayer.screens

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.abc.model.SignupRequest
import com.example.abc.model.otpRequest
import com.example.queuemanagementapp.presentationlayer.Viewmodel.AuthViewModel
import com.example.queuemanagementapp.R
import com.example.queuemanagementapp.ui.theme.Purple80
import com.example.queuemanagementapp.ui.theme.fontFamily
import kotlinx.coroutines.delay


@Composable
fun OtpScreen(navController: NavController) {
    var otpvalue by rememberSaveable { mutableStateOf("") }
    var showTimer by remember { mutableStateOf(true) }
    var isPlaying by rememberSaveable { mutableStateOf(false) }
    var speed by rememberSaveable { mutableStateOf(1f) }
    val authViewModel = hiltViewModel<AuthViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_ln75cwak))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = true,
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(key1 = progress) {
            if (progress == 0f) {
                isPlaying = true
            }
            if (progress == 1f) {
                isPlaying = false
            }
        }
        LottieAnimation(
            composition,
            {progress},
            modifier = Modifier
                .size(200.dp)
                .padding(top = 30.dp),
        )
        Text(
            text = "OTP VERIFICATION",
            style = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
        )
        Text(
            text = "Enter the otp sent to your email",
            color = Color(0xFF9da8b4),
            style = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.heightIn(50.dp))
        Surface {
            BasicTextField(
                value = otpvalue,
                onValueChange ={
                    if(it.length<= 4 ){ otpvalue = it}
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                decorationBox = {
                    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.height(40.dp)){
                        repeat(4) { index->
                            val  char  = when{
                                index >= otpvalue.length -> ""
                                else -> otpvalue[index].toString()
                            }
                            val isFocused = otpvalue.length == index
                            Text(
                                text = char,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .border(
                                        if (isFocused) 2.dp else 1.dp,
                                        if (isFocused) colorResource(id = R.color.new_color) else Color.Black,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(color = Color.White)
                                    .padding(2.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = fontFamily
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.heightIn(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Didn't receive the OTP?", color = Color.Black, style = TextStyle(
                    fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp
                )
            )

            TextButton(
                enabled = !showTimer,
                onClick = {
                    showTimer = true
                    isPlaying = true
                    authViewModel.signupUser(SignupRequest(Signup.username.value))
                },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Resend Otp", style = TextStyle(
                        brush =  if(!showTimer)Brush.horizontalGradient(
                               listOf(colorResource(id = R.color.new_color), Purple80))
                            else Brush.horizontalGradient(
                                listOf( Color.Gray,Color.Gray)
                        ),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ), fontSize = 18.sp
                )

            }
        }
        Spacer(modifier = Modifier.heightIn(8.dp))
        if (showTimer) {
            CountdownTimer(initialMillis = 30000, onTimerFinished = { showTimer = false })
        }
        Button(contentPadding = PaddingValues(vertical = 0.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(0.7f)
                .background(
                    Color(0xFF3384DD), RoundedCornerShape(8.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            onClick = {
                keyboardController?.hide()
                authViewModel.otp(otpRequest(Signup.username.value,otpvalue,Signup.password.value ))
            }) {
            Text(
                text = "Verify", color = Color.White,
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )
        }
    }
}



@Composable
fun CountdownTimer(
    initialMillis: Long,
    onTimerFinished: () -> Unit
) {
    var timeLeft by rememberSaveable { mutableStateOf(initialMillis) }
    LaunchedEffect(timeLeft) {
        val countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }
        countDownTimer.start()
    }
    if (timeLeft > 0) {
        val txt = timeLeft / 1000
        LaunchedEffect(Unit) {
            run {
                delay(450)
            }
        }
        Text(
            text =   if(timeLeft.toString().length > 2)"00:$txt" else "00:0$txt",
            color = Color.Black,
            style = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
    }
}
