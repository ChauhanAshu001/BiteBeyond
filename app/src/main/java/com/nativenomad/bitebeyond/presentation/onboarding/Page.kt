package com.nativenomad.bitebeyond.presentation.onboarding

import androidx.annotation.DrawableRes
import com.nativenomad.bitebeyond.R

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image: Int
)

val pages=listOf(
    Page(title="Order For Food",
        description = "Crave it? Get it! Order your favorite meals in minutes with fresh flavors, fast delivery, and zero hassle.",
        image= R.drawable.onboarding1),

    Page(title=" Not your regular food app",
        description = "Use the AI to choose food when in rush ",
        image=R.drawable.bot),

    Page(title="You ask we prepare",
        description = "All the best restaurants with their top menu waiting for you, they can’t wait for your order!!",
        image = R.drawable.burger),

//    Page(title="Let's GO",
//        description = "",
//        image = R.drawable.bot)
)