package com.nativenomad.bitebeyond.models

import androidx.compose.ui.graphics.painter.Painter

data class NavItems(
    val label: String,
    val icon: Painter //ImageVector is used under the hood in things like Icons.Default.Home
)
