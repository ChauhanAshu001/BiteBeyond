package com.nativenomad.adminbitebeyond.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.nativenomad.adminbitebeyond.presentation.Paddings.IndicatorSize

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize:Int,   //how many pages are there
    selectedPage:Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary, //default select and unselect colors are set but specifying these colors as parameter allow to modify them while calling function.
    unselectedColor: Color = Color.Gray) {

    Row(modifier=modifier,horizontalArrangement = Arrangement.SpaceBetween){
        repeat(pageSize){page->     //page is basically a loop variable like i is there in for loop. It ranges from [0,pageSize).
            Box(modifier= Modifier.size(IndicatorSize).clip(CircleShape)
                .background(if(page==selectedPage) selectedColor else unselectedColor))

        }
    }
}