package com.example.gamecandor.data

import kotlinx.serialization.Serializable

@Serializable
data class GameSave(
    val name: String,
    val playedCards: Set<Int> = emptySet()
)

@Serializable
data class GameSaveList(
    val language: LanguageText = LanguageText.RU,
    val textSize: TextSize = TextSize.MEDIUM,
    val games: MutableList<GameSave> = mutableListOf()
)