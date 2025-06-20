package com.nativenomad.bitebeyond.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme

@Composable
fun HomeScreen() {
    Box(modifier=Modifier.background(Color.Red).fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="HomeScreen")
    }

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
    BiteBeyondTheme {
        HomeScreen()
    }
}