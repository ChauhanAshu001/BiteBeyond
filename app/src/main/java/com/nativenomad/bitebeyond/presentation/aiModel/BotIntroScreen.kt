package com.nativenomad.bitebeyond.presentation.aiModel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.navgraph.Routes

@Composable
fun BotIntroScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                listOf(colorResource(R.color.lightOrange), colorResource(R.color.white))
            ))
            .padding(bottom = 85.dp)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // AI Avatar or Icon
            Text(
                text = "\uD83E\uDD16",
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Headline
            Text(
                text = "Your Personal AI, helps you decide your order",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF3E2723),
                    letterSpacing = 1.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "I'm ready to whip up the perfect meal based on your preferences.\n\nJust tap 'Next' and let's order something magical! 🍳✨",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF5D4037),
                    lineHeight = 24.sp
                ),
                textAlign = TextAlign.Center
            )
        }

        // Invisible-looking "Next" button in bottom right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Next ➜",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = colorResource(R.color.lightOrange).copy(alpha = 0.5f), // Blends with bg
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { navController.navigate(Routes.AiScreen.route) }
                    .padding(12.dp)
                    .background(Color.Transparent)
            )
        }
    }
}