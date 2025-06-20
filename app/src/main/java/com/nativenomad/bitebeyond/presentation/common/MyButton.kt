package com.nativenomad.bitebeyond.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.R

@Composable
fun MyButton(
    text:String,
    onClick:()->Unit,
){
    Button(onClick=onClick,colors= ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.darkOrange),
        contentColor = Color.White
    ),
        shape= RoundedCornerShape(size= 10.dp)
    ) {
        Text(text=text,style= MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold)
    }
}