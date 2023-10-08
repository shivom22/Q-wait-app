package com.example.queuemanagementapp.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.queuemanagementapp.R
import com.example.queuemanagementapp.ui.theme.Purple80

@Composable
fun GradientBorderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val borderColor = if (isFocused) {
       Brush.horizontalGradient(listOf(
           colorResource(id = R.color.new_color),
           Purple80
       ))
    } else {
        Brush.horizontalGradient(listOf(Color.Gray, Color.Gray))
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .border(
                BorderStroke(
                width = 1.dp,
                brush = borderColor
            )
            )
            .padding(8.dp)
            .focusModifier()
            .onFocusChanged { newFocusState ->
                isFocused = newFocusState.isFocused
            }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    focusManager.clearFocus()
                }
            }
    )
}

@Composable
fun GradientBorderTextFieldDemo() {
    var text by remember { mutableStateOf("") }

    GradientBorderTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
@Composable
@Preview(showBackground = true)
fun preview(){
    GradientBorderTextFieldDemo()
}
