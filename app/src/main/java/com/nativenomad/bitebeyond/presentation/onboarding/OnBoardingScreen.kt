package com.nativenomad.bitebeyond.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.presentation.Paddings.MediumPadding2
import com.nativenomad.bitebeyond.presentation.Paddings.PageIndicatorWidth
import com.nativenomad.bitebeyond.presentation.common.MyButton
import com.nativenomad.bitebeyond.presentation.onboarding.components.OnBoardingPage
import com.nativenomad.bitebeyond.presentation.onboarding.components.PageIndicator
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit    //event is a lambda function
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(initialPage = 0) { pages.size }
        val buttonState= remember{
            derivedStateOf {
                when(pagerState.currentPage){
                    0-> listOf("", "Next")
                    1-> listOf("Back", "Next")
                    2-> listOf("Back", "Get Started")
//                    3-> listOf("Back","Get Started")
                    else -> listOf("", "")
                }
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            HorizontalPager(state = pagerState) { index ->
                OnBoardingPage(page = pages[index])
            }
        }

        // Buttons stay visible now
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()
                if (buttonState.value[0].isNotEmpty()) {
                    MyButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                MyButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                event(OnBoardingEvent.SaveAppEntry)
                                event(OnBoardingEvent.NavigateToSignUp)
                            } else {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
    }

}