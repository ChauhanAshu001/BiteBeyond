package com.nativenomad.bitebeyond.presentation.onboarding.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.Paddings.MediumPadding1
import com.nativenomad.bitebeyond.presentation.Paddings.MediumPadding2
import com.nativenomad.bitebeyond.presentation.onboarding.Page
import com.nativenomad.bitebeyond.presentation.onboarding.pages
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme
@Composable
fun OnBoardingPage(
    page: Page,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            modifier = Modifier
                .fillMaxSize().fillMaxHeight(0.6f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        // Optional: Gradient overlay for better text visibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Foreground content (text box) over the image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MediumPadding2),
            verticalArrangement = Arrangement.Bottom // Pushes it to bottom
        ) {
            Box(
                modifier = Modifier
                    .width(311.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(colorResource(id = R.color.lightOrange))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = page.title,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = page.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true) //for light mode
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true) //for night mode
@Composable
fun OnBoardingPagePreview(){
    BiteBeyondTheme {
        OnBoardingPage(
            page=pages[0]
        )
    }
}