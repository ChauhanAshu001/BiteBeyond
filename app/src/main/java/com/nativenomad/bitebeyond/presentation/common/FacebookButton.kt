package com.nativenomad.bitebeyond.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.R

@Composable
fun FacebookButton(modifier: Modifier) {
    Button(
        onClick = { /* Handle Facebook Sign-In */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1877F2),
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.facebook), // Replace with Facebook icon
            contentDescription = "Facebook Icon",
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Facebook", color = Color.White)
    }
}