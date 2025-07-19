package com.nativenomad.bitebeyond.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormatTimeStamp {
    fun formatTimeStamp(timeStamp : Long):String{
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date(timeStamp))
    }
}