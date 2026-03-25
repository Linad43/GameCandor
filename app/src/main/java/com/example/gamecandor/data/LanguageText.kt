package com.example.gamecandor.data

import android.content.Context
import com.example.gamecandor.R

enum class LanguageText {
    RU
}
fun LanguageText.toReadable(context: Context):String{
    return when(this) {
        LanguageText.RU -> context.getString(R.string.text_ru)
    }
}