package com.example.gamecandor.data

import kotlinx.serialization.Serializable

@Serializable
data class GameSave(
    val name: String,
    val playedCards: Set<Int> = emptySet()
)

@Serializable
data class GameSaveList(
    val games: MutableList<GameSave> = mutableListOf()
)