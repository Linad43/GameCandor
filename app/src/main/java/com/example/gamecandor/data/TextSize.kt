package com.example.gamecandor.data

import android.content.Context
import com.example.gamecandor.R

enum class TextSize {
    SMALL,
    MEDIUM,
    LARGE
}
fun TextSize.toReadable(context: Context):String{
    return when(this){
        TextSize.SMALL -> context.getString(R.string.small)
        TextSize.MEDIUM -> context.getString(R.string.medium)
        TextSize.LARGE -> context.getString(R.string.large)
    }
}
