package com.nativenomad.bitebeyond.presentation.common

import androidx.compose.foundation.BorderStroke
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
fun GoogleButton(modifier: Modifier) {
    Button(
        onClick = { /* Handle Google Sign-In */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google), // Replace with Google icon
            contentDescription = "Google Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Google", color = Color.Black)
    }

}
