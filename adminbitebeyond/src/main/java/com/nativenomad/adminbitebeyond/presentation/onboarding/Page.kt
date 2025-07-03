package com.nativenomad.adminbitebeyond.presentation.onboarding

import androidx.annotation.DrawableRes
import com.nativenomad.adminbitebeyond.R

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image: Int
)

val pages=listOf(
    Page(title="Hey Partner,Welcome !!",
        description = "Manager your restaurant and orders here.",
        image= R.drawable.on_boardingscreen1),

    Page(title="Streamline Order Management",
        description = "View, update, and fulfill customer orders in real time. From new requests to completed deliveries, stay in control at every step. ",
        image=R.drawable.on_boardingscreen2),

    Page(title="Customize Your Menu",
        description = "Add new dishes, update prices, or mark items as unavailable. Keep your digital menu fresh and aligned with your kitchen.",
        image = R.drawable.on_boardingscreen3),

//    Page(title="Let's GO",
//        description = "",
//        image = R.drawable.bot)
)