package com.nativenomad.bitebeyond.presentation.onboarding.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
){

    Column(modifier=modifier){
        Image(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.6F),
            painter= painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit)

        Spacer(modifier= Modifier.height(MediumPadding1))

        //this text is for title
        Text(text=page.title,
            modifier= Modifier.padding(MediumPadding2),
            style= MaterialTheme.typography.displaySmall,
            fontWeight= FontWeight.ExtraBold,
            color= colorResource(id= R.color.darkOrange)
        )
        //this text is for description
        Text(text=page.description,
            modifier= Modifier.padding(MediumPadding2),
            style= MaterialTheme.typography.bodyMedium,
            color= Color.Black
        )
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