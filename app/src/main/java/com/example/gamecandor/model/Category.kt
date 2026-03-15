package com.example.gamecandor.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.gamecandor.R

enum class Category(
    @DrawableRes val background:Int,
    @ColorRes val color:Int
) {
    ACTIVITY(
        R.drawable.bg_activity,
        R.color.activity_color
    ),
    BODY(
        R.drawable.bg_body,
        R.color.body_color
    ),
    CONTACTS(
        R.drawable.bg_contacts,
        R.color.contacts_color
    ),
    MEANING(
        R.drawable.bg_meaning,
        R.color.meaning_color
    )
}